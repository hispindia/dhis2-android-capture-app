package org.medhis2yes

import androidx.lifecycle.MutableLiveData
import androidx.work.WorkInfo
import org.medhis2yes.common.di.TestingInjector
import org.medhis2yes.common.preferences.PreferencesTestingModule
import org.medhis2yes.data.schedulers.SchedulerModule
import org.medhis2yes.data.schedulers.SchedulersProviderImpl
import org.medhis2yes.data.server.ServerModule
import org.medhis2yes.data.user.UserModule
import org.medhis2yes.usescases.sync.MockedWorkManagerModule
import org.medhis2yes.usescases.sync.MockedWorkManagerController
import org.medhis2yes.utils.analytics.AnalyticsModule
import org.hisp.dhis.android.core.D2Manager

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
        D2Manager.setTestingDatabase(DB_TO_IMPORT, USERNAME)
        D2Manager.blockingInstantiateD2(ServerModule.getD2Configuration(this))

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
