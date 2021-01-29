package org.dhis2hiv.usescases.main

import dagger.Module
import dagger.Provides
import org.dhis2hiv.data.dagger.PerActivity
import org.dhis2hiv.data.filter.FilterPresenter
import org.dhis2hiv.data.prefs.PreferenceProvider
import org.dhis2hiv.data.schedulers.SchedulerProvider
import org.dhis2hiv.data.service.workManager.WorkManagerController
import org.dhis2hiv.utils.filters.FilterManager
import org.dhis2hiv.utils.filters.FiltersAdapter
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
