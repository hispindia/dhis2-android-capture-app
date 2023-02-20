package org.dhis2_haparent.usescases.programEventDetail.eventMap

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import org.dhis2_haparent.commons.di.dagger.PerFragment
import org.dhis2_haparent.commons.filters.FilterManager
import org.dhis2_haparent.commons.prefs.PreferenceProvider
import org.dhis2_haparent.commons.schedulers.SchedulerProvider
import org.dhis2_haparent.usescases.programEventDetail.ProgramEventDetailRepository

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
