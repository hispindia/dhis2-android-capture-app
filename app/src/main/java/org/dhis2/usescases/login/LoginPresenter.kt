package org.dhis2.usescases.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Base64
import android.util.Log
import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope
import androidx.annotation.VisibleForTesting
import co.infinum.goldfinger.Goldfinger
import com.mapbox.mapboxsdk.Mapbox
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.dhis2.App
import org.dhis2.commons.prefs.Preference
import org.dhis2.commons.prefs.Preference.Companion.PIN
import org.dhis2.commons.prefs.Preference.Companion.SESSION_LOCKED
import org.dhis2.commons.prefs.PreferenceProvider
import org.dhis2.commons.prefs.SECURE_PASS
import org.dhis2.commons.prefs.SECURE_SERVER_URL
import org.dhis2.commons.prefs.SECURE_USER_NAME
import org.dhis2.commons.schedulers.SchedulerProvider
import org.dhis2.data.fingerprint.FingerPrintController
import org.dhis2.data.fingerprint.Type
import org.dhis2.data.server.UserManager
import org.dhis2.usescases.main.MainActivity
import org.dhis2.utils.Constants.PREFS_URLS
import org.dhis2.utils.Constants.PREFS_USERS
import org.dhis2.utils.Constants.SERVER
import org.dhis2.utils.Constants.USER
import org.dhis2.utils.Constants.USER_ASKED_CRASHLYTICS
import org.dhis2.utils.Constants.USER_TEST_ANDROID
import org.dhis2.utils.TestingCredential
import org.dhis2.utils.analytics.ACCOUNT_RECOVERY
import org.dhis2.utils.analytics.AnalyticsHelper
import org.dhis2.utils.analytics.CLICK
import org.dhis2.utils.analytics.LOGIN
import org.dhis2.utils.analytics.SERVER_QR_SCANNER
import org.dhis2.utils.reporting.CrashReportController
import org.hisp.dhis.android.core.maintenance.D2Error
import org.hisp.dhis.android.core.maintenance.D2ErrorCode
import org.hisp.dhis.android.core.systeminfo.SystemInfo
import org.hisp.dhis.android.core.user.openid.OpenIDConnectConfig
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
    private val analyticsHelper: AnalyticsHelper,
    private val crashReportController: CrashReportController
) {

    private var userManager: UserManager? = null
    var disposable: CompositeDisposable = CompositeDisposable()

    private var canHandleBiometrics: Boolean? = null

    private var existing_uid: String? = null
    private var rows_check: String? = null
    private var existing_user_pass: String? = null

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
        view.hideKeyboard()
        analyticsHelper.setEvent(LOGIN, CLICK, LOGIN)
        if (!preferenceProvider.getBoolean(USER_ASKED_CRASHLYTICS, false)) {
            view.showCrashlyticsDialog()
        } else {
            view.showLoginProgress(true)
        }
    }
    fun sendPostRequest(user:String,pass:String,serverUrl:String,userName:String, password:String) {
        Log.d("sendPostRequest","");
        existing_uid=null;
        val pair = String.format("%s:%s", userName, password)
        val creds = Base64.encodeToString(pair.toByteArray(), Base64.NO_WRAP)

        var reqParam = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8")
        reqParam += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")



        val mURL1 = URL(serverUrl+"/api/trackedEntityInstances/query.json?ou=bpwodUz0F0c&ouMode=ACCESSIBLE&program=xV81JTF1M3C&attribute=stuluwAni9L:LIKE:"+user)
        val mURL2 = URL(serverUrl+"/api/trackedEntityInstances/query.json?ou=bpwodUz0F0c&ouMode=ACCESSIBLE&program=xV81JTF1M3C&attribute=stuluwAni9L:eq:"+user+"-"+pass)
        doAsync {
            with(mURL2.openConnection() as HttpURLConnection) {
                Log.d("method1-mURL2","");
                // optional default is GET
                requestMethod = "GET"
                setRequestProperty("Authorization", "Basic $creds")
                setRequestProperty("Content-Type", "application/json;charset=UTF-8")
                val data = inputStream.bufferedReader().readText()
                rows_check=data.substring(
                    data.indexOf("rows") ,
                    data.lastIndexOf("rows") + 10,
                )
                Log.d("method1-rows_check",rows_check.toString());
                if(rows_check!!.contains("[]"))
                {
                    Log.d("inside rows--","");
                    doAsync {
                        with(mURL1.openConnection() as HttpURLConnection) {
                            // optional default is GET

                            requestMethod = "GET"
                            setRequestProperty("Authorization", "Basic $creds")
                            setRequestProperty("Content-Type", "application/json;charset=UTF-8")

                            BufferedReader(InputStreamReader(inputStream)).use {
                                val response = StringBuffer()

                                var userpass=user+"-"+pass
                                Log.d("inside rows--response",response.toString());
                                if (response.contains(user))
                                {
                                    existing_user_pass=response.substring(
                                        response.indexOf(user) ,
                                        response.lastIndexOf(user) + 20,
                                    )
                                    existing_user_pass=existing_user_pass!!.substring(existing_user_pass!!.length-12);
                                    existing_user_pass=existing_user_pass!!.substring(1,12)


                                    val settings3: SharedPreferences = Mapbox.getApplicationContext()
                                        .getSharedPreferences("user_uid", 0)
                                    val editor = settings3.edit()
                                    editor.putString("tei-uid", existing_uid)
                                    editor.apply();
                                    Log.d("tei-uid---",existing_uid.toString());

                                    if(response.contains(userpass))
                                    {
                                        Log.d("tei-uid---userpass--",existing_uid.toString());
                                        val settings2: SharedPreferences = Mapbox.getApplicationContext()
                                            .getSharedPreferences("user_uid", 0)
                                        val editor = settings2.edit()
                                        editor.putString("tei-uid", existing_uid)
                                        editor.apply();

                                        println("Success---- : $userpass")
                                    }
                                    else if(!response.contains(userpass))
                                    {
                                        Log.d("tei-uid---not--up--",existing_uid.toString());
                                        val settings1: SharedPreferences = Mapbox.getApplicationContext()
                                            .getSharedPreferences("user_uid", 0)
                                        val editor = settings1.edit()
                                        editor.putString("tei-uid", "pwderror")
                                        editor.apply();

                                        println("Invalid pass for : $userpass")
                                    }
                                }

                                else if (!response.contains(user))
                                {

                                    var body1="{\"attributes\":[{\"attribute\":\"stuluwAni9L\",\"value\":\""+userpass+"\"}],\"enrollments\":[{\"program\":\"xV81JTF1M3C\",\"orgUnit\":\"bpwodUz0F0c\",\"status\":\"ACTIVE\",\"events\":[{\"status\":\"ACTIVE\",\"programStage\":\"p7aXvRYiToO\",\"program\":\"xV81JTF1M3C\",\"orgUnit\":\"bpwodUz0F0c\"}]}],\"orgUnit\":\"bpwodUz0F0c\",\"trackedEntityType\":\"BL4SAWpbS5b\"}"

                                    println("User createed---- for: $userpass")
                                    val mURL = URL(serverUrl+"/api/trackedEntityInstances")
                                    doAsync {
                                        with(mURL.openConnection() as HttpURLConnection) {
                                            // optional default is GET
                                            requestMethod = "POST"
                                            setRequestProperty("Authorization", "Basic $creds")
                                            setRequestProperty("Content-Type", "application/json;charset=UTF-8")

                                            val wr = OutputStreamWriter(getOutputStream());
                                            wr.write(body1);
                                            wr.flush();

                                            println("URL : $url")
                                            println("Response Code : $responseCode")

                                            BufferedReader(InputStreamReader(inputStream)).use {
                                                val response = StringBuffer()
                                                var inputLine = it.readLine()

                                                var new_uid=inputLine.substring(
                                                    inputLine.indexOf("api/trackedEntityInstances/") ,
                                                    inputLine.lastIndexOf("api/trackedEntityInstances/") + 38,
                                                )

                                                new_uid=new_uid.substring(new_uid.length-11);
                                                val settings: SharedPreferences = Mapbox.getApplicationContext()
                                                    .getSharedPreferences("user_uid", 0)
                                                val editor = settings.edit()
                                                editor.putString("tei-uid", new_uid)
                                                editor.apply();
                                                Log.d("new_uid-----",new_uid);
                                                while (inputLine != null) {
                                                    response.append(inputLine)
                                                    inputLine = it.readLine()
                                                }

                                                println("Response : $response")
                                            }
                                        }
                                    }
                                }
                                println("Response : $response")
                            }


                        }
                    }

                }
                else
                {

                    existing_uid=data.substring(
                        data.indexOf("rows") ,
                        data.lastIndexOf("rows") + 20,
                    )
                    existing_uid=existing_uid!!.substring(existing_uid!!.length-12);
                    existing_uid=existing_uid!!.substring(1,12)
                    Log.d("existing_uid---rec-",existing_uid.toString());

                    val settings3: SharedPreferences = Mapbox.getApplicationContext()
                        .getSharedPreferences("user_uid", 0)
                    val editor = settings3.edit()
                    editor.putString("tei-uid", existing_uid)
                    editor.apply();
                }

//                if (data!!.length.equals(0))
//                {
//                    doAsync {
//                        with(mURL1.openConnection() as HttpURLConnection) {
//                            // optional default is GET
//
//                            requestMethod = "GET"
//                            setRequestProperty("Authorization", "Basic $creds")
//                            setRequestProperty("Content-Type", "application/json;charset=UTF-8")
//
//                            BufferedReader(InputStreamReader(inputStream)).use {
//                                val response = StringBuffer()
//
//                                var userpass=user+"-"+pass
//
//                                if (response.contains(user))
//                                {
//                                    existing_user_pass=response.substring(
//                                        response.indexOf(user) ,
//                                        response.lastIndexOf(user) + 20,
//                                    )
//                                    existing_user_pass=existing_user_pass!!.substring(existing_user_pass!!.length-12);
//                                    existing_user_pass=existing_user_pass!!.substring(1,12)
//                                    Log.d("test","tee");
//
//                                    val settings3: SharedPreferences = getApplicationContext().getSharedPreferences("user_uid", 0)
//                                    val editor = settings3.edit()
//                                    editor.putString("tei-uid", existing_uid)
//                                    editor.apply();
//
//
//                                    if(response.contains(userpass))
//                                    {
//                                        val settings2: SharedPreferences = getApplicationContext().getSharedPreferences("user_uid", 0)
//                                        val editor = settings2.edit()
//                                        editor.putString("tei-uid", existing_uid)
//                                        editor.apply();
//
//                                        println("Success---- : $userpass")
//                                    }
//                                    else if(!response.contains(userpass))
//                                    {
//                                        val settings1: SharedPreferences = getApplicationContext().getSharedPreferences("user_uid", 0)
//                                        val editor = settings1.edit()
//                                        editor.putString("tei-uid", "pwderror")
//                                        editor.apply();
//
//                                        println("Invalid pass for : $userpass")
//                                    }
//                                }
//
//                                else if (!response.contains(user))
//                                {
//
//                                    var body1="{\"attributes\":[{\"attribute\":\"stuluwAni9L\",\"value\":\""+userpass+"\"}],\"enrollments\":[{\"program\":\"xV81JTF1M3C\",\"orgUnit\":\"bpwodUz0F0c\",\"status\":\"ACTIVE\",\"events\":[{\"status\":\"ACTIVE\",\"programStage\":\"p7aXvRYiToO\",\"program\":\"xV81JTF1M3C\",\"orgUnit\":\"bpwodUz0F0c\"}]}],\"orgUnit\":\"bpwodUz0F0c\",\"trackedEntityType\":\"BL4SAWpbS5b\"}"
//                                    println("User createed---- for: $userpass")
//                                    val mURL = URL(serverUrl+"/api/trackedEntityInstances")
//                                    doAsync {
//                                        with(mURL.openConnection() as HttpURLConnection) {
//                                            // optional default is GET
//                                            requestMethod = "POST"
//                                            setRequestProperty("Authorization", "Basic $creds")
//                                            setRequestProperty("Content-Type", "application/json;charset=UTF-8")
//
//                                            val wr = OutputStreamWriter(getOutputStream());
//                                            wr.write(body1);
//                                            wr.flush();
//
//                                            println("URL : $url")
//                                            println("Response Code : $responseCode")
//
//                                            BufferedReader(InputStreamReader(inputStream)).use {
//                                                val response = StringBuffer()
//                                                var inputLine = it.readLine()
//
//                                                var new_uid=inputLine.substring(
//                                                    inputLine.indexOf("api/trackedEntityInstances/") ,
//                                                    inputLine.lastIndexOf("api/trackedEntityInstances/") + 38,
//                                                )
//                                                new_uid=new_uid.substring(new_uid.length-11);
//                                                val settings: SharedPreferences = getApplicationContext().getSharedPreferences("user_uid", 0)
//                                                val editor = settings.edit()
//                                                editor.putString("tei-uid", new_uid)
//                                                editor.apply();
//
//                                                while (inputLine != null) {
//                                                    response.append(inputLine)
//                                                    inputLine = it.readLine()
//                                                }
//                                                println("Response : $response")
//                                            }
//                                        }
//                                    }
//                                }
//                                println("Response : $response")
//                            }
//
//
//                        }
//                    }
//                    Log.d("test","failw");
//                }
//                else
//                {
//                    existing_uid=data.substring(
//                        data.indexOf("rows") ,
//                        data.lastIndexOf("rows") + 20,
//                    )
//                    existing_uid=existing_uid!!.substring(existing_uid!!.length-12);
//                    existing_uid=existing_uid!!.substring(1,12)
//                    Log.d("test","tee");
//
//                    val settings3: SharedPreferences = getApplicationContext().getSharedPreferences("user_uid", 0)
//                    val editor = settings3.edit()
//                    editor.putString("tei-uid", existing_uid)
//                    editor.apply();
//                }

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
                                        USER,
                                        userManager.d2.userModule()
                                            .userCredentials()
                                            .blockingGet()
                                            .username()
                                    )
                                    setValue(SESSION_LOCKED, false)
                                    setValue(PIN, null)
                                }
                                trackUserInfo()
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

    fun openIdLogin(config: OpenIDConnectConfig) {
        disposable.add(
            Observable.just(
                (view.abstracContext.applicationContext as App).createServerComponent()
                    .userManager()
            )
                .flatMap { userManager ->
                    this.userManager = userManager
                    userManager.logIn(config)
                }
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        view.openOpenIDActivity(it)
                    },
                    {
                        Timber.e(it)
                    }
                )
        )
    }

    fun handleAuthResponseData(serverUrl: String, data: Intent, requestCode: Int) {
        userManager?.let { userManager ->
            disposable.add(
                userManager.handleAuthData(serverUrl, data, requestCode)
                    .map<Response<Any>> { user ->
                        run {
                            with(preferenceProvider) {
                                setValue(
                                    USER,
                                    userManager.d2.userModule()
                                        .userCredentials()
                                        .blockingGet()
                                        .username()
                                )
                                setValue(SESSION_LOCKED, false)
                                setValue(PIN, null)
                                setValue(SERVER, "$serverUrl/api")
                            }
                            trackUserInfo()
                            Response.success<Any>(null)
                        }
                    }.subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .subscribe(
                        {
                            this.handleResponse(it, "", serverUrl)
                        },
                        {
                            this.handleError(it, serverUrl, "", "")
                        }
                    )
            )
        }
    }

    private fun trackUserInfo() {
        val username = preferenceProvider.getString(USER)
        val server = preferenceProvider.getString(SERVER)

        crashReportController.trackServer(server)
        crashReportController.trackUser(username, server)
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
