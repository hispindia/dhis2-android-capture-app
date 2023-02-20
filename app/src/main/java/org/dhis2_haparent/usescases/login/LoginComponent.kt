package org.dhis2_haparent.usescases.login

import dagger.Subcomponent
import org.dhis2_haparent.commons.di.dagger.PerActivity
import org.dhis2_haparent.data.fingerprint.FingerPrintModule

@PerActivity
@Subcomponent(modules = [LoginModule::class, FingerPrintModule::class])
interface LoginComponent {
    fun inject(loginActivity: LoginActivity)
}
