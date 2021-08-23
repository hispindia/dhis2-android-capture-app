package org.medhis2yes.usescases.splash

import dagger.Module
import dagger.Provides
import javax.inject.Named
import org.medhis2yes.data.dagger.PerActivity
import org.medhis2yes.data.prefs.PreferenceProvider
import org.medhis2yes.data.schedulers.SchedulerProvider
import org.medhis2yes.data.server.ServerComponent
import org.medhis2yes.data.server.UserManager
import org.medhis2yes.usescases.splash.SplashActivity.Companion.FLAG
import org.medhis2yes.utils.reporting.CrashReportController

/**
 * QUADRAM. Created by ppajuelo on 07/02/2018.
 */

@Module
class SplashModule internal constructor(
    private val splashView: SplashView,
    serverComponent: ServerComponent?
) {

    private val userManager: UserManager? = serverComponent?.userManager()

    @Provides
    @PerActivity
    fun providePresenter(
        schedulerProvider: SchedulerProvider,
        preferenceProvider: PreferenceProvider,
        crashReportController: CrashReportController
    ): SplashPresenter {
        return SplashPresenter(
            splashView,
            userManager,
            schedulerProvider,
            preferenceProvider,
            crashReportController
        )
    }

    @Provides
    @PerActivity
    @Named(FLAG)
    fun provideFlag(): String {
        return if (userManager?.d2 != null && userManager.isUserLoggedIn.blockingFirst()) {
            val systemSetting =
                userManager.d2.systemSettingModule().systemSetting().flag().blockingGet()
            if (systemSetting != null) {
                systemSetting.value() ?: ""
            } else {
                ""
            }
        } else {
            ""
        }
    }
}
