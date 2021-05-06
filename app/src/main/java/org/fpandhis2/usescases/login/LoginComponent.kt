package org.fpandhis2.usescases.login

import dagger.Subcomponent
import org.fpandhis2.data.dagger.PerActivity
import org.fpandhis2.data.fingerprint.FingerPrintModule

@PerActivity
@Subcomponent(modules = [LoginModule::class, FingerPrintModule::class])
interface LoginComponent {
    fun inject(loginActivity: LoginActivity)
}
