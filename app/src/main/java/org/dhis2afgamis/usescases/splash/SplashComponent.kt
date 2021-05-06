package org.dhis2afgamis.usescases.splash

import dagger.Subcomponent
import org.dhis2afgamis.data.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [SplashModule::class])
interface SplashComponent {
    fun inject(splashActivity: SplashActivity)
}
