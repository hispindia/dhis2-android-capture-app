package org.dhis2.usescases.programEventDetail.eventMap

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import org.dhis2.data.dagger.PerFragment
import org.dhis2.data.prefs.PreferenceProvider
import org.dhis2.data.schedulers.SchedulerProvider
import org.dhis2.usescases.programEventDetail.ProgramEventDetailRepository
import org.dhis2.utils.filters.FilterManager

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
