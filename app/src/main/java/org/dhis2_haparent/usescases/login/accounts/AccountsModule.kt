package org.dhis2_haparent.usescases.login.accounts

import dagger.Module
import dagger.Provides
import org.dhis2_haparent.commons.di.dagger.PerActivity
import org.hisp.dhis.android.core.D2

@Module
class AccountsModule {

    @Provides
    fun provideRepository(d2: D2): AccountRepository {
        return AccountRepository(d2)
    }

    @Provides
    @PerActivity
    fun provideViewModelFactory(
        accountRepository: AccountRepository
    ): AccountsViewModelFactory {
        return AccountsViewModelFactory(accountRepository)
    }
}
