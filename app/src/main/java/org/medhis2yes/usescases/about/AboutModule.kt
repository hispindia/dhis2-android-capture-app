package org.medhis2yes.usescases.about

import dagger.Module
import dagger.Provides
import org.medhis2yes.data.dagger.PerFragment
import org.medhis2yes.data.schedulers.SchedulerProvider
import org.medhis2yes.data.user.UserRepository
import org.hisp.dhis.android.core.D2

@Module
class AboutModule(val view: AboutView) {
    @Provides
    @PerFragment
    fun providesPresenter(
        d2: D2,
        provider: SchedulerProvider,
        userRepository: UserRepository
    ): AboutPresenter {
        return AboutPresenter(view, d2, provider, userRepository)
    }
}
