package org.dhis2_haparent.usescases.sync

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import org.dhis2_haparent.commons.di.dagger.PerActivity
import org.dhis2_haparent.commons.prefs.PreferenceProvider
import org.dhis2_haparent.commons.schedulers.SchedulerProvider
import org.dhis2_haparent.data.server.ServerComponent
import org.dhis2_haparent.data.service.workManager.WorkManagerController

@PerActivity
@Subcomponent(modules = [SyncModule::class])
interface SyncComponent {
    fun inject(syncActivity: SyncActivity)
}

@Module
class SyncModule(private val view: SyncView, serverComponent: ServerComponent?) {

    private val userManager = serverComponent?.userManager()

    @Provides
    @PerActivity
    fun providePresenter(
        schedulerProvider: SchedulerProvider,
        workManagerController: WorkManagerController,
        preferences: PreferenceProvider
    ): SyncPresenter {
        return SyncPresenter(
            view,
            userManager,
            schedulerProvider,
            workManagerController,
            preferences
        )
    }
}
