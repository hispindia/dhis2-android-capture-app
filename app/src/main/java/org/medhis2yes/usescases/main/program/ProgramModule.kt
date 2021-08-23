package org.medhis2yes.usescases.main.program

import dagger.Module
import dagger.Provides
import org.medhis2yes.data.dagger.PerFragment
import org.medhis2yes.data.dhislogic.DhisProgramUtils
import org.medhis2yes.data.dhislogic.DhisTrackedEntityInstanceUtils
import org.medhis2yes.data.filter.FilterPresenter
import org.medhis2yes.data.prefs.PreferenceProvider
import org.medhis2yes.data.schedulers.SchedulerProvider
import org.medhis2yes.utils.analytics.matomo.MatomoAnalyticsController
import org.medhis2yes.utils.filters.FilterManager
import org.medhis2yes.utils.resources.ResourceManager
import org.hisp.dhis.android.core.D2

@Module
@PerFragment
class ProgramModule(private val view: ProgramView) {

    @Provides
    @PerFragment
    internal fun programPresenter(
        homeRepository: HomeRepository,
        schedulerProvider: SchedulerProvider,
        preferenceProvider: PreferenceProvider,
        filterManager: FilterManager,
        matomoAnalyticsController: MatomoAnalyticsController
    ): ProgramPresenter {
        return ProgramPresenter(
            view,
            homeRepository,
            schedulerProvider,
            preferenceProvider,
            filterManager,
            matomoAnalyticsController
        )
    }

    @Provides
    @PerFragment
    internal fun homeRepository(
        d2: D2,
        filterPresenter: FilterPresenter,
        dhisProgramUtils: DhisProgramUtils,
        dhisTrackedEntityInstanceUtils: DhisTrackedEntityInstanceUtils,
        schedulerProvider: SchedulerProvider,
        resourceManager: ResourceManager
    ): HomeRepository {
        return HomeRepositoryImpl(
            d2,
            filterPresenter,
            dhisProgramUtils,
            dhisTrackedEntityInstanceUtils,
            resourceManager,
            schedulerProvider
        )
    }

    @Provides
    @PerFragment
    internal fun providesAdapter(presenter: ProgramPresenter): ProgramModelAdapter {
        return ProgramModelAdapter(presenter)
    }

    @Provides
    @PerFragment
    fun provideAnimations(): ProgramAnimation {
        return ProgramAnimation()
    }
}
