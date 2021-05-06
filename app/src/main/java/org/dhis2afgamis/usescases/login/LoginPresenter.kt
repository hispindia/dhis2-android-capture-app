package org.dhis2afgamis.usescases.login

import android.os.Build
import android.util.Base64
import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope
import androidx.annotation.VisibleForTesting
import co.infinum.goldfinger.Goldfinger
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.dhis2afgamis.App
import org.dhis2afgamis.data.fingerprint.FingerPrintController
import org.dhis2afgamis.data.fingerprint.Type
import org.dhis2afgamis.data.prefs.Preference
import org.dhis2afgamis.data.prefs.Preference.Companion.PIN
import org.dhis2afgamis.data.prefs.Preference.Companion.SESSION_LOCKED
import org.dhis2afgamis.data.prefs.PreferenceProvider
import org.dhis2afgamis.data.schedulers.SchedulerProvider
import org.dhis2afgamis.data.server.UserManager
import org.dhis2afgamis.usescases.main.MainActivity
import org.dhis2afgamis.utils.Constants.PREFS_URLS
import org.dhis2afgamis.utils.Constants.PREFS_USERS
import org.dhis2afgamis.utils.Constants.SECURE_PASS
import org.dhis2afgamis.utils.Constants.SECURE_SERVER_URL
import org.dhis2afgamis.utils.Constants.SECURE_USER_NAME
import org.dhis2afgamis.utils.Constants.SERVER
import org.dhis2afgamis.utils.Constants.USER
import org.dhis2afgamis.utils.Constants.USER_ASKED_CRASHLYTICS
import org.dhis2afgamis.utils.Constants.USER_TEST_ANDROID
import org.dhis2afgamis.utils.TestingCredential
import org.dhis2afgamis.utils.analytics.ACCOUNT_RECOVERY
import org.dhis2afgamis.utils.analytics.AnalyticsHelper
import org.dhis2afgamis.utils.analytics.CLICK
import org.dhis2afgamis.utils.analytics.LOGIN
import org.dhis2afgamis.utils.analytics.SERVER_QR_SCANNER
import org.hisp.dhis.android.core.maintenance.D2Error
import org.hisp.dhis.android.core.maintenance.D2ErrorCode
import org.hisp.dhis.android.core.systeminfo.SystemInfo
import org.jetbrains.anko.doAsync
import retrofit2.Response
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class LoginPresenter(
    private val view: LoginContracts.View,
    private val preferenceProvider: PreferenceProvider,
    private val schedulers: SchedulerProvider,
    private val fingerPrintController: FingerPrintController,
    private val analyticsHelper: AnalyticsHelper
) {

    private var userManager: UserManager? = null
    var disposable: CompositeDisposable = CompositeDisposable()

    private var canHandleBiometrics: Boolean? = null

    fun init(userManager: UserManager?) {
        this.userManager = userManager
        this.userManager?.let {
            disposable.add(
                it.isUserLoggedIn
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .subscribe(
                        { isUserLoggedIn ->
                            val isSessionLocked =
                                preferenceProvider.getBoolean(SESSION_LOCKED, false)
                            if (isUserLoggedIn && !isSessionLocked) {
                                view.startActivity(MainActivity::class.java, null, true, true, null)
                            } else if (isSessionLocked) {
                                view.showUnlockButton()
                            }
                            if (!isUserLoggedIn) {
                                val serverUrl =
                                    preferenceProvider.getString(
                                        SECURE_SERVER_URL,
                                        view.getDefaultServerProtocol()
                                    )
                                val user = preferenceProvider.getString(SECURE_USER_NAME, "")
                                if (!serverUrl.isNullOrEmpty() && !user.isNullOrEmpty()) {
                                    view.setUrl(serverUrl)
                                    view.setUser(user)
                                } else {
                                    view.setUrl(view.getDefaultServerProtocol())
                                }
                            }
                        },
                        { exception -> Timber.e(exception) }
                    )
            )
        } ?: view.setUrl(view.getDefaultServerProtocol())
    }

    fun checkServerInfoAndShowBiometricButton() {
        userManager?.let { userManager ->
            disposable.add(
                Observable.just(getSystemInfoIfUserIsLogged(userManager))
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .subscribe(
                        { systemInfo ->
                            if (systemInfo.contextPath() != null) {
                                view.setUrl(systemInfo.contextPath() ?: "")
                                preferenceProvider.getString(USER, "")?.also {
                                    view.setUser(it)
                                }
                            } else {
                                val isSessionLocked =
                                    preferenceProvider.getBoolean(SESSION_LOCKED, false)
                                if (!isSessionLocked) {
                                    val serverUrl =
                                        preferenceProvider.getString(
                                            SECURE_SERVER_URL,
                                            view.getDefaultServerProtocol()
                                        )
                                    val user = preferenceProvider.getString(SECURE_USER_NAME, "")
                                    if (!serverUrl.isNullOrEmpty() && !user.isNullOrEmpty()) {
                                        view.setUrl(serverUrl)
                                        view.setUser(user)
                                    }
                                } else {
                                    view.setUrl(view.getDefaultServerProtocol())
                                }
                            }
                        },
                        { Timber.e(it) }
                    )
            )
        } ?: view.setUrl(view.getDefaultServerProtocol())

        showBiometricButtonIfVersionIsGreaterThanM(view)
    }

    private fun getSystemInfoIfUserIsLogged(userManager: UserManager): SystemInfo {
        return if (userManager.isUserLoggedIn.blockingFirst() &&
            userManager.d2.systemInfoModule().systemInfo().blockingGet() != null
        ) {
            userManager.d2.systemInfoModule().systemInfo().blockingGet()
        } else {
            SystemInfo.builder().build()
        }
    }

    private fun showBiometricButtonIfVersionIsGreaterThanM(view: LoginContracts.View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            disposable.add(
                Observable.just(fingerPrintController.hasFingerPrint())
                    .filter { canHandleBiometrics ->
                        this.canHandleBiometrics = canHandleBiometrics
                        canHandleBiometrics && preferenceProvider.contains(SECURE_SERVER_URL)
                    }
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .subscribe(
                        { view.showBiometricButton() },
                        { Timber.e(it) }
                    )
            )
        }
    }

    fun onButtonClick() {
        //post language

        view.hideKeyboard()
        analyticsHelper.setEvent(LOGIN, CLICK, LOGIN)
        if (!preferenceProvider.getBoolean(USER_ASKED_CRASHLYTICS, false)) {
            view.showCrashlyticsDialog()
        } else {
            view.showLoginProgress(true)
        }
    }



    fun sendPostRequest(lang_sel:String,serverUrl:String,userName:String, password:String) {
        val pair = String.format("%s:%s", userName, password)
        val creds = Base64.encodeToString(pair.toByteArray(), Base64.NO_WRAP)

//        val url = serverUrl+"/api/userSettings/keyDbLocale?user="+userName+"&value=en"
//
//        val client = OkHttpClient()
//
//        val JSON = MediaType.get("application/json; charset=utf-8")
//        val body = RequestBody.create(JSON, "{\"data\":\"\"}")
//        doAsync {
//            val request = Request.Builder()
//                    .addHeader("Authorization", "Basic $creds")
//                    .url(url)
//                    .post(body)
//                    .build()
//
//            val  response = client . newCall (request).execute()
//
//            println(response.request())
//            println(response.body()!!.string())
//        }



        var reqParam = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8")
        reqParam += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")

        if (lang_sel=="English")
        {
            val mURL = URL(serverUrl+"/api/userSettings/keyDbLocale?user="+userName+"&value=en")
            doAsync {
                with(mURL.openConnection() as HttpURLConnection) {
                    // optional default is GET
                    requestMethod = "POST"
                    setRequestProperty("Authorization", "Basic $creds")
                    setRequestProperty("Content-Type", "application/json;charset=UTF-8")
                    val wr = OutputStreamWriter(getOutputStream());
                    wr.write(reqParam);
                    wr.flush();

                    println("URL : $url")
                    println("Response Code : $responseCode")

                    BufferedReader(InputStreamReader(inputStream)).use {
                        val response = StringBuffer()

                        var inputLine = it.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()
                        }
                        println("Response : $response")
                    }
                }
            }

            val mURL1 = URL(serverUrl+"/api/userSettings/keyUiLocale?user="+userName+"&value=en")
            doAsync {
                with(mURL1.openConnection() as HttpURLConnection) {
                    // optional default is GET
                    requestMethod = "POST"
                    setRequestProperty("Authorization", "Basic $creds")
                    setRequestProperty("Content-Type", "application/json;charset=UTF-8")
                    val wr = OutputStreamWriter(getOutputStream());
                    wr.write(reqParam);
                    wr.flush();

                    println("URL : $url")
                    println("Response Code : $responseCode")

                    BufferedReader(InputStreamReader(inputStream)).use {
                        val response = StringBuffer()

                        var inputLine = it.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()
                        }
                        println("Response : $response")
                    }
                }
            }
        }
        else if(lang_sel=="Dari")
        {
            val mURL = URL(serverUrl+"/api/userSettings/keyDbLocale?user="+userName+"&value=fa")
            doAsync {
                with(mURL.openConnection() as HttpURLConnection) {
                    // optional default is GET
                    requestMethod = "POST"
                    setRequestProperty("Authorization", "Basic $creds")
                    setRequestProperty("Content-Type", "application/json;charset=UTF-8")
                    val wr = OutputStreamWriter(getOutputStream());
                    wr.write(reqParam);
                    wr.flush();

                    println("URL : $url")
                    println("Response Code : $responseCode")
                    println("Response Message : $responseMessage")

                    BufferedReader(InputStreamReader(inputStream)).use {
                        val response = StringBuffer()

                        var inputLine = it.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()
                        }
                        println("Response : $response")
                    }
                }
            }
            val mURL1 = URL(serverUrl+"/api/userSettings/keyUiLocale?user="+userName+"&value=fa")
            doAsync {
                with(mURL1.openConnection() as HttpURLConnection) {
                    // optional default is GET
                    requestMethod = "POST"
                    setRequestProperty("Authorization", "Basic $creds")
                    setRequestProperty("Content-Type", "application/json;charset=UTF-8")
                    val wr = OutputStreamWriter(getOutputStream());
                    wr.write(reqParam);
                    wr.flush();

                    println("URL : $url")
                    println("Response Code : $responseCode")

                    BufferedReader(InputStreamReader(inputStream)).use {
                        val response = StringBuffer()

                        var inputLine = it.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()
                        }
                        println("Response : $response")
                    }
                }
            }
        }
        else if(lang_sel=="Pashto")
        {
            val mURL = URL(serverUrl+"/api/userSettings/keyDbLocale?user="+userName+"&value=ps")
            doAsync {
                with(mURL.openConnection() as HttpURLConnection) {
                    // optional default is GET
                    requestMethod = "POST"
                    setRequestProperty("Authorization", "Basic $creds")
                    setRequestProperty("Content-Type", "application/json;charset=UTF-8")
                    val wr = OutputStreamWriter(getOutputStream());
                    wr.write(reqParam);
                    wr.flush();

                    println("URL : $url")
                    println("Response Code : $responseCode")

                    BufferedReader(InputStreamReader(inputStream)).use {
                        val response = StringBuffer()

                        var inputLine = it.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()
                        }
                        println("Response : $response")
                    }
                }
            }
            val mURL1 = URL(serverUrl+"/api/userSettings/keyUiLocale?user="+userName+"&value=ps")
            doAsync {
                with(mURL1.openConnection() as HttpURLConnection) {
                    // optional default is GET
                    requestMethod = "POST"
                    setRequestProperty("Authorization", "Basic $creds")
                    setRequestProperty("Content-Type", "application/json;charset=UTF-8")
                    val wr = OutputStreamWriter(getOutputStream());
                    wr.write(reqParam);
                    wr.flush();

                    println("URL : $url")
                    println("Response Code : $responseCode")

                    BufferedReader(InputStreamReader(inputStream)).use {
                        val response = StringBuffer()

                        var inputLine = it.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()
                        }
                        println("Response : $response")
                    }
                }
            }
        }




    }

    fun logIn(serverUrl: String, userName: String, pass: String) {
        disposable.add(
            Observable.just(
                (view.abstracContext.applicationContext as App).createServerComponent()
                    .userManager()
            )
                .flatMap { userManager ->
                    preferenceProvider.setValue(SERVER, "$serverUrl/api")
                    this.userManager = userManager
                    userManager.logIn(userName.trim { it <= ' ' }, pass, serverUrl)
                        .map<Response<Any>> { user ->
                            run {
                                with(preferenceProvider) {
                                    setValue(
                                        USER
                                    )
                                    setValue(SESSION_LOCKED, false)
                                    setValue(PIN, null)
                                }
                                Response.success<Any>(null)
                            }
                        }
                }
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        this.handleResponse(it, userName, serverUrl)
                    },
                    {
                        this.handleError(it, serverUrl, userName, pass)
                    }
                )
        )
    }

    fun onQRClick() {
        analyticsHelper.setEvent(SERVER_QR_SCANNER, CLICK, SERVER_QR_SCANNER)
        view.navigateToQRActivity()
    }

    fun onDestroy() {
        disposable.clear()
    }

    fun logOut() {
        userManager?.let {
            disposable.add(
                it.d2.userModule().logOut()
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .subscribe(
                        {
                            preferenceProvider.setValue(SESSION_LOCKED, false)
                            view.handleLogout()
                        },
                        { view.handleLogout() }
                    )
            )
        }
    }

    @VisibleForTesting
    fun handleResponse(userResponse: Response<*>, userName: String, server: String) {
        view.showLoginProgress(false)
        if (userResponse.isSuccessful) {
            if (view.isNetworkAvailable()) {
                preferenceProvider.setValue(Preference.INITIAL_SYNC_DONE, false)
            }

            val updatedServer = (preferenceProvider.getSet(PREFS_URLS, HashSet()) as HashSet)
            if (!updatedServer.contains(server)) {
                updatedServer.add(server)
            }
            val updatedUsers = (preferenceProvider.getSet(PREFS_USERS, HashSet()) as HashSet)
            if (!updatedUsers.contains(userName)) {
                updatedUsers.add(userName)
            }

            preferenceProvider.setValue(PREFS_URLS, updatedServer)
            preferenceProvider.setValue(PREFS_USERS, updatedUsers)

            view.saveUsersData()
        }
    }

    private fun handleError(
        throwable: Throwable,
        serverUrl: String,
        userName: String,
        pass: String
    ) {
        Timber.e(throwable)
        if (throwable is D2Error && throwable.errorCode() == D2ErrorCode.ALREADY_AUTHENTICATED) {
            userManager?.d2?.userModule()?.blockingLogOut()
            logIn(serverUrl, userName, pass)
//            sendPostRequest(serverUrl,userName,pass)
        } else {
            view.renderError(throwable)
        }
        view.showLoginProgress(false)
    }

    fun stopReadingFingerprint() {
        fingerPrintController.cancel()
    }

    fun canHandleBiometrics(): Boolean? {
        return canHandleBiometrics
    }

    fun areSameCredentials(serverUrl: String, userName: String, pass: String): Boolean {
        return preferenceProvider.areCredentialsSet() &&
            preferenceProvider.areSameCredentials(serverUrl, userName, pass)
    }

    fun saveUserCredentials(serverUrl: String, userName: String, pass: String) {
        preferenceProvider.saveUserCredentials(serverUrl, userName, pass)
    }

    fun onFingerprintClick() {
        disposable.add(

            fingerPrintController.authenticate(view.getPromptParams())
                .map { result ->
                    if (preferenceProvider.contains(
                        SECURE_SERVER_URL,
                        SECURE_USER_NAME,
                        SECURE_PASS
                    )
                    ) {
                        Result.success(result)
                    } else {
                        Result.failure(Exception(EMPTY_CREDENTIALS))
                    }
                }
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        if (it.isFailure) {
                            view.showEmptyCredentialsMessage()
                        } else if (it.isSuccess && it.getOrNull()?.type == Type.SUCCESS) {
                            view.showCredentialsData(
                                Goldfinger.Type.SUCCESS,
                                preferenceProvider.getString(SECURE_SERVER_URL)!!,
                                preferenceProvider.getString(SECURE_USER_NAME)!!,
                                preferenceProvider.getString(SECURE_PASS)!!
                            )
                        } else if (it.getOrNull()?.type == Type.ERROR) {
                            view.showCredentialsData(
                                Goldfinger.Type.ERROR,
                                it.getOrNull()?.message!!
                            )
                        }
                    },
                    {
                        view.displayMessage(AUTH_ERROR)
                    }
                )
        )
    }

    fun onAccountRecovery() {
        analyticsHelper.setEvent(ACCOUNT_RECOVERY, CLICK, ACCOUNT_RECOVERY)
        view.openAccountRecovery()
    }

    fun getAutocompleteData(
        testingCredentials: List<TestingCredential>
    ): Pair<MutableList<String>, MutableList<String>> {
        val urls = preferenceProvider.getSet(PREFS_URLS, emptySet())!!.toMutableList()
        val users = preferenceProvider.getSet(PREFS_USERS, emptySet())!!.toMutableList()

        urls.let {
            for (testingCredential in testingCredentials) {
                if (!it.contains(testingCredential.server_url)) {
                    it.add(testingCredential.server_url)
                }
            }
        }

        preferenceProvider.setValue(PREFS_URLS, HashSet(urls))

        users.let {
            if (!it.contains(USER_TEST_ANDROID)) {
                it.add(USER_TEST_ANDROID)
            }
        }

        preferenceProvider.setValue(PREFS_USERS, HashSet(users))

        return Pair(urls, users)
    }

    // TODO Remove this when we remove the userManager from the presenter
    @RestrictTo(Scope.TESTS)
    fun setUserManager(userManager: UserManager) {
        this.userManager = userManager
    }

    companion object {
        const val EMPTY_CREDENTIALS = "Empty credentials"
        const val AUTH_ERROR = "AUTH ERROR"
    }
}
