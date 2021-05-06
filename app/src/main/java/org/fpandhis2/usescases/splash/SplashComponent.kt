package org.fpandhis2.usescases.splash

import dagger.Subcomponent
import org.fpandhis2.data.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [SplashModule::class])
interface SplashComponent {
    fun inject(splashActivity: SplashActivity)
}
