package org.dhis2_haparent.usescases.splash

import dagger.Subcomponent
import org.dhis2_haparent.commons.di.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [SplashModule::class])
interface SplashComponent {
    fun inject(splashActivity: SplashActivity)
}
