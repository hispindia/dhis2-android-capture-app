package org.dhis2afgamis.usescases.login

import dagger.Subcomponent
import org.dhis2afgamis.data.dagger.PerActivity
import org.dhis2afgamis.data.fingerprint.FingerPrintModule

@PerActivity
@Subcomponent(modules = [LoginModule::class, FingerPrintModule::class])
interface LoginComponent {
    fun inject(loginActivity: LoginActivity)
}
