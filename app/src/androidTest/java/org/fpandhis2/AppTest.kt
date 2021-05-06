package org.fpandhis2

import androidx.lifecycle.MutableLiveData
import androidx.work.WorkInfo
import org.fpandhis2.common.preferences.PreferencesTestingModule
import org.fpandhis2.data.schedulers.SchedulerModule
import org.fpandhis2.data.schedulers.SchedulersProviderImpl
import org.fpandhis2.data.server.ServerModule
import org.fpandhis2.data.user.UserModule
import org.fpandhis2.usescases.sync.MockedWorkManagerModule
import org.fpandhis2.usescases.sync.MockedWorkManagerController
import org.fpandhis2.utils.analytics.AnalyticsModule
import org.hisp.dhis.android.core.D2Manager
import org.matomo.sdk.Tracker

class AppTest : App() {

    val mutableWorkInfoStatuses = MutableLiveData<List<WorkInfo>>()

    @Override
    override fun onCreate() {
        wantToImportDB = true
        super.onCreate()
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

    override fun getTracker(): Tracker? {
        return null
    }

    companion object {
        const val DB_TO_IMPORT = "127-0-0-1-8080_android_unencrypted.db"
        const val USERNAME = "android"
    }
}
