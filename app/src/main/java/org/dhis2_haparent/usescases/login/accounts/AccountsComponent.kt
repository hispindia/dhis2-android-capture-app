package org.dhis2_haparent.usescases.login.accounts

import dagger.Subcomponent
import org.dhis2_haparent.commons.di.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [AccountsModule::class])
interface AccountsComponent {
    fun inject(accountsActivity: AccountsActivity)
}
