package org.medhis2yes.usescases.login

import android.content.Context
import dagger.Module
import dagger.Provides
import org.medhis2yes.data.dagger.PerActivity
import org.medhis2yes.data.fingerprint.FingerPrintController
import org.medhis2yes.data.prefs.PreferenceProvider
import org.medhis2yes.data.schedulers.SchedulerProvider
import org.medhis2yes.usescases.login.auth.OpenIdProviders
import org.medhis2yes.utils.analytics.AnalyticsHelper
import org.medhis2yes.utils.reporting.CrashReportController

/**
 * QUADRAM. Created by ppajuelo on 07/02/2018.
 */

@Module
@PerActivity
class LoginModule(private val view: LoginContracts.View) {

    @Provides
    @PerActivity
    fun providePresenter(
        preferenceProvider: PreferenceProvider,
        schedulerProvider: SchedulerProvider,
        fingerPrintController: FingerPrintController,
        analyticsHelper: AnalyticsHelper,
        crashReportController: CrashReportController
    ): LoginPresenter {
        return LoginPresenter(
            view,
            preferenceProvider,
            schedulerProvider,
            fingerPrintController,
            analyticsHelper,
            crashReportController
        )
    }

    @Provides
    @PerActivity
    fun openIdProviders(context: Context): OpenIdProviders {
        return OpenIdProviders(context)
    }
}
