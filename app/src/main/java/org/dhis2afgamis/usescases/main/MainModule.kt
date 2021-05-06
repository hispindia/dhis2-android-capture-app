package org.dhis2afgamis.usescases.main

import dagger.Module
import dagger.Provides
import org.dhis2afgamis.data.dagger.PerActivity
import org.dhis2afgamis.data.filter.FilterPresenter
import org.dhis2afgamis.data.prefs.PreferenceProvider
import org.dhis2afgamis.data.schedulers.SchedulerProvider
import org.dhis2afgamis.data.service.workManager.WorkManagerController
import org.dhis2afgamis.utils.filters.FilterManager
import org.dhis2afgamis.utils.filters.FiltersAdapter
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
