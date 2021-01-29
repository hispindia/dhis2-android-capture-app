package org.dhis2hiv.usescases.splash

import dagger.Subcomponent
import org.dhis2hiv.data.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [SplashModule::class])
interface SplashComponent {
    fun inject(splashActivity: SplashActivity)
}
