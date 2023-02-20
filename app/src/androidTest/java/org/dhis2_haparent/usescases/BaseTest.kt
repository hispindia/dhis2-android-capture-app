package org.dhis2_haparent.usescases

import android.content.Context
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.intent.Intents
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import org.dhis2_haparent.AppTest
import org.dhis2_haparent.AppTest.Companion.DB_TO_IMPORT
import org.dhis2_haparent.common.BaseRobot
import org.dhis2_haparent.common.di.TestingInjector
import org.dhis2_haparent.common.keystore.KeyStoreRobot
import org.dhis2_haparent.common.keystore.KeyStoreRobot.Companion.KEYSTORE_PASSWORD
import org.dhis2_haparent.common.keystore.KeyStoreRobot.Companion.KEYSTORE_USERNAME
import org.dhis2_haparent.common.keystore.KeyStoreRobot.Companion.PASSWORD
import org.dhis2_haparent.common.keystore.KeyStoreRobot.Companion.USERNAME
import org.dhis2_haparent.common.mockwebserver.MockWebServerRobot
import org.dhis2_haparent.common.preferences.PreferencesRobot
import org.dhis2_haparent.common.rules.DisableAnimations
import org.dhis2_haparent.commons.idlingresource.CountingIdlingResourceSingleton
import org.dhis2_haparent.commons.idlingresource.SearchIdlingResourceSingleton
import org.dhis2_haparent.commons.prefs.Preference
import org.dhis2_haparent.form.ui.idling.FormCountingIdlingResource
import org.dhis2_haparent.usescases.teiDashboard.dashboardfragments.teidata.TeiDataIdlingResourceSingleton
import org.junit.After
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.rules.Timeout
import java.util.concurrent.TimeUnit

open class BaseTest {

    @JvmField
    protected var context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private var isIntentsEnable = false
    private lateinit var keyStoreRobot: KeyStoreRobot
    lateinit var preferencesRobot: PreferencesRobot
    lateinit var mockWebServerRobot: MockWebServerRobot

    protected open fun getPermissionsToBeAccepted() = arrayOf<String>()

    @get:Rule
    val timeout: Timeout = Timeout(120000, TimeUnit.MILLISECONDS)

    @get:Rule
    var permissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @Before
    @Throws(Exception::class)
    open fun setUp() {
        injectDependencies()
        registerCountingIdlingResource()
        setupCredentials()
    }

    private fun injectDependencies() {
        TestingInjector.apply {
            keyStoreRobot = providesKeyStoreRobot(context)
            preferencesRobot = providesPreferencesRobot(context)
            mockWebServerRobot = providesMockWebserverRobot(context)
        }
    }

    private fun registerCountingIdlingResource() {
        IdlingRegistry.getInstance().register(
            CountingIdlingResourceSingleton.countingIdlingResource,
            FormCountingIdlingResource.countingIdlingResource,
            SearchIdlingResourceSingleton.countingIdlingResource,
            TeiDataIdlingResourceSingleton.countingIdlingResource
        )
    }

    private fun unregisterCountingIdlingResource() {
        IdlingRegistry.getInstance()
            .unregister(
                CountingIdlingResourceSingleton.countingIdlingResource,
                FormCountingIdlingResource.countingIdlingResource,
                SearchIdlingResourceSingleton.countingIdlingResource,
                TeiDataIdlingResourceSingleton.countingIdlingResource
            )
    }

    fun setupMockServer() {
        mockWebServerRobot.start()
    }

    @After
    @Throws(Exception::class)
    open fun teardown() {
        closeKeyboard()
        disableIntents()
        cleanPreferences()
        cleanKeystore()
        stopMockServer()
        unregisterCountingIdlingResource()
    }

    fun enableIntents() {
        if (!isIntentsEnable) {
            Intents.init()
            isIntentsEnable = true
        }
    }

    fun setupCredentials() {
        val keyStoreRobot = TestingInjector.providesKeyStoreRobot(context)
        keyStoreRobot.apply {
            setData(KEYSTORE_USERNAME, USERNAME)
            setData(KEYSTORE_PASSWORD, PASSWORD)
        }
    }

    fun setDatePicker() {
        preferencesRobot.saveValue(Preference.DATE_PICKER, true)
    }

    private fun closeKeyboard(){
        BaseRobot().closeKeyboard()
    }

    private fun disableIntents() {
        if (isIntentsEnable) {
            Intents.release()
            isIntentsEnable = false
        }
    }

    private fun cleanPreferences() {
        preferencesRobot.cleanPreferences()
    }

    private fun cleanKeystore() {
        keyStoreRobot.apply {
            removeData(KEYSTORE_USERNAME)
            removeData(KEYSTORE_PASSWORD)
        }
    }

    private fun stopMockServer() {
        mockWebServerRobot.shutdown()
    }

    fun cleanLocalDatabase() {
        (context.applicationContext as AppTest).deleteDatabase(DB_TO_IMPORT)
    }

    companion object {
        @ClassRule
        @JvmField
        val disableAnimationsTestRule = DisableAnimations()
        const val MOCK_SERVER_URL = "http://127.0.0.1:8080"
        const val API = "api"
    }
}
