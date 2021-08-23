package org.medhis2yes.usescases.main

import dagger.Module
import dagger.Provides
import org.medhis2yes.data.dagger.PerActivity
import org.medhis2yes.data.filter.FilterRepository
import org.medhis2yes.data.prefs.PreferenceProvider
import org.medhis2yes.data.schedulers.SchedulerProvider
import org.medhis2yes.data.service.workManager.WorkManagerController
import org.medhis2yes.utils.analytics.matomo.MatomoAnalyticsController
import org.medhis2yes.utils.filters.FilterManager
import org.medhis2yes.utils.filters.FiltersAdapter
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
        filterManager: FilterManager,
        filterRepository: FilterRepository,
        matomoAnalyticsController: MatomoAnalyticsController
    ): MainPresenter {
        return MainPresenter(
            view,
            d2,
            schedulerProvider,
            preferences,
            workManagerController,
            filterManager,
            filterRepository,
            matomoAnalyticsController
        )
    }

    @Provides
    @PerActivity
    fun providesNewFilterAdapter(): FiltersAdapter {
        return FiltersAdapter()
    }
}
