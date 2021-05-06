package org.fpandhis2.usescases.main

import dagger.Module
import dagger.Provides
import org.fpandhis2.data.dagger.PerActivity
import org.fpandhis2.data.filter.FilterPresenter
import org.fpandhis2.data.prefs.PreferenceProvider
import org.fpandhis2.data.schedulers.SchedulerProvider
import org.fpandhis2.data.service.workManager.WorkManagerController
import org.fpandhis2.utils.filters.FilterManager
import org.fpandhis2.utils.filters.FiltersAdapter
import org.hisp.dhis.android.core.D2

@Module
class MainModule(val view: MainView) {

    @Provides
    @PerActivity
    fun homePresenter(
        d2: D2,
        schedulerProvider: SchedulerProvider,
        preferences: PreferenceProvider,
        workManagerController: WorkManagerController,
        filterManager: FilterManager
    ): MainPresenter {
        return MainPresenter(
            view,
            d2,
            schedulerProvider,
            preferences,
            workManagerController,
            filterManager
        )
    }

    @Provides
    @PerActivity
    fun provideFiltersAdapter(filterPresenter: FilterPresenter): FiltersAdapter {
        return FiltersAdapter(FiltersAdapter.ProgramType.ALL, filterPresenter)
    }
}
