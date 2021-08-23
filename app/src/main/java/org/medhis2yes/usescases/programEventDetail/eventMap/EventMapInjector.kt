package org.medhis2yes.usescases.programEventDetail.eventMap

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import org.medhis2yes.data.dagger.PerFragment
import org.medhis2yes.data.prefs.PreferenceProvider
import org.medhis2yes.data.schedulers.SchedulerProvider
import org.medhis2yes.usescases.programEventDetail.ProgramEventDetailRepository
import org.medhis2yes.utils.filters.FilterManager

@PerFragment
@Subcomponent(modules = [EventMapModule::class])
interface EventMapComponent {
    fun inject(fragment: EventMapFragment)
}

@Module
class EventMapModule(
    val view: EventMapFragmentView
) {
    @Provides
    @PerFragment
    fun providePresenter(
        filterManager: FilterManager,
        programEventDetailRepository: ProgramEventDetailRepository,
        preferences: PreferenceProvider,
        schedulers: SchedulerProvider
    ): EventMapPresenter {
        return EventMapPresenter(
            view,
            filterManager,
            programEventDetailRepository,
            preferences,
            schedulers
        )
    }
}
