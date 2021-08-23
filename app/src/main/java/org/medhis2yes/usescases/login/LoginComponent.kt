package org.medhis2yes.usescases.login

import dagger.Subcomponent
import org.medhis2yes.data.dagger.PerActivity
import org.medhis2yes.data.fingerprint.FingerPrintModule

@PerActivity
@Subcomponent(modules = [LoginModule::class, FingerPrintModule::class])
interface LoginComponent {
    fun inject(loginActivity: LoginActivity)
}
