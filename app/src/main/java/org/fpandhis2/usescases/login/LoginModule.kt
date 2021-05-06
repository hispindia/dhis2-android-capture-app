package org.fpandhis2.usescases.login

import dagger.Module
import dagger.Provides
import org.fpandhis2.data.dagger.PerActivity
import org.fpandhis2.data.fingerprint.FingerPrintController
import org.fpandhis2.data.prefs.PreferenceProvider
import org.fpandhis2.data.schedulers.SchedulerProvider
import org.fpandhis2.utils.analytics.AnalyticsHelper

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
        analyticsHelper: AnalyticsHelper
    ): LoginPresenter {
        return LoginPresenter(
            view,
            preferenceProvider,
            schedulerProvider,
            fingerPrintController,
            analyticsHelper
        )
    }
}
