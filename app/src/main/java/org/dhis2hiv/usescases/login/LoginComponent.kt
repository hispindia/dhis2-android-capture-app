package org.dhis2hiv.usescases.login

import dagger.Subcomponent
import org.dhis2hiv.data.dagger.PerActivity
import org.dhis2hiv.data.fingerprint.FingerPrintModule

@PerActivity
@Subcomponent(modules = [LoginModule::class, FingerPrintModule::class])
interface LoginComponent {
    fun inject(loginActivity: LoginActivity)
}
