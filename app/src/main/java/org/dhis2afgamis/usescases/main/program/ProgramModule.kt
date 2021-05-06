package org.dhis2afgamis.usescases.main.program

import dagger.Module
import dagger.Provides
import org.dhis2afgamis.data.dagger.PerFragment
import org.dhis2afgamis.data.dhislogic.DhisProgramUtils
import org.dhis2afgamis.data.dhislogic.DhisTrackedEntityInstanceUtils
import org.dhis2afgamis.data.filter.FilterPresenter
import org.dhis2afgamis.data.prefs.PreferenceProvider
import org.dhis2afgamis.data.schedulers.SchedulerProvider
import org.dhis2afgamis.utils.filters.FilterManager
import org.dhis2afgamis.utils.resources.ResourceManager
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
        filterManager: FilterManager
    ): ProgramPresenter {
        return ProgramPresenter(
            view,
            homeRepository,
            schedulerProvider,
            preferenceProvider,
            filterManager
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
