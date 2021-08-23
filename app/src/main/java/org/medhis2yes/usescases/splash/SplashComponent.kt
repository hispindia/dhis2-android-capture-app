package org.medhis2yes.usescases.splash

import dagger.Subcomponent
import org.medhis2yes.data.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [SplashModule::class])
interface SplashComponent {
    fun inject(splashActivity: SplashActivity)
}
