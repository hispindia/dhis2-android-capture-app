package org.medhis2yes.usescases.sync

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import org.medhis2yes.data.dagger.PerActivity
import org.medhis2yes.data.prefs.PreferenceProvider
import org.medhis2yes.data.schedulers.SchedulerProvider
import org.medhis2yes.data.server.ServerComponent
import org.medhis2yes.data.service.workManager.WorkManagerController

@PerActivity
@Subcomponent(modules = [SyncModule::class])
interface SyncComponent {
    fun inject(syncActivity: SyncActivity)
}

@Module
@PerActivity
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
