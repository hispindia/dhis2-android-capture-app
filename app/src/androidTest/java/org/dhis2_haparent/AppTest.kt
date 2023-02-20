package org.dhis2_haparent

import androidx.lifecycle.MutableLiveData
import androidx.work.WorkInfo
import org.dhis2_haparent.common.di.TestingInjector
import org.dhis2_haparent.common.keystore.KeyStoreRobot
import org.dhis2_haparent.common.preferences.PreferencesTestingModule
import org.dhis2_haparent.commons.schedulers.SchedulerModule
import org.dhis2_haparent.commons.schedulers.SchedulersProviderImpl
import org.dhis2_haparent.data.server.ServerModule
import org.dhis2_haparent.data.user.UserModule
import org.dhis2_haparent.usescases.BaseTest.Companion.MOCK_SERVER_URL
import org.dhis2_haparent.usescases.sync.MockedWorkManagerModule
import org.dhis2_haparent.usescases.sync.MockedWorkManagerController
import org.dhis2_haparent.utils.analytics.AnalyticsModule
import org.hisp.dhis.android.core.D2Manager
import org.hisp.dhis.android.core.D2Manager.blockingInstantiateD2
import org.hisp.dhis.android.core.D2Manager.setCredentials
import org.hisp.dhis.android.core.D2Manager.setTestingSecureStore

class AppTest : App() {

    val mutableWorkInfoStatuses = MutableLiveData<List<WorkInfo>>()

    @Override
    override fun onCreate() {
        populateDBIfNeeded()
        super.onCreate()
    }

    private fun populateDBIfNeeded() {
        TestingInjector.provideDBImporter(applicationContext).apply {
            copyDatabaseFromAssetsIfNeeded()
        }
    }

    @Override
    override fun setUpServerComponent() {
        D2Manager.setTestingDatabase(MOCK_SERVER_URL, DB_TO_IMPORT, USERNAME)
        val keystoreRobot = TestingInjector.providesKeyStoreRobot(applicationContext)
        keystoreRobot.apply {
            setData(KeyStoreRobot.KEYSTORE_USERNAME, KeyStoreRobot.USERNAME)
            setData(KeyStoreRobot.KEYSTORE_PASSWORD, KeyStoreRobot.PASSWORD)
        }
        D2Manager.also {
            setTestingSecureStore(TestingInjector.getStorage())
            blockingInstantiateD2(ServerModule.getD2Configuration(this))
            setCredentials(KeyStoreRobot.KEYSTORE_USERNAME, KeyStoreRobot.PASSWORD)
        }

        serverComponent = appComponent.plus(ServerModule())

        setUpUserComponent()
    }

    @Override
    override fun setUpUserComponent() {
        super.setUpUserComponent()

        val userManager = if (serverComponent == null) {
            null
        } else {
            serverComponent!!.userManager()
        }
        if (userManager != null) {
            userComponent = serverComponent!!.plus(UserModule())
        }
    }

    override fun prepareAppComponent(): AppComponent.Builder {
        return DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .schedulerModule(SchedulerModule(SchedulersProviderImpl()))
            .analyticsModule(AnalyticsModule())
            .preferenceModule(PreferencesTestingModule())
            //.coroutineDispatchers(DispatcherTestingModule())
            .customDispatcher(CustomDispatcherModule())
            .workManagerController(
                MockedWorkManagerModule(
                    MockedWorkManagerController(
                        mutableWorkInfoStatuses
                    )
                )
            )
    }

    companion object {
        const val DB_TO_IMPORT = "127-0-0-1-8080_android_unencrypted.db"
        const val USERNAME = "android"
    }
}
