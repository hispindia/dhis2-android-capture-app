package org.medhis2yes.usescases.programEventDetail.eventList

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import org.medhis2yes.data.dagger.PerFragment
import org.medhis2yes.data.prefs.PreferenceProvider
import org.medhis2yes.data.schedulers.SchedulerProvider
import org.medhis2yes.usescases.programEventDetail.ProgramEventDetailRepository
import org.medhis2yes.utils.filters.FilterManager

@PerFragment
@Subcomponent(modules = [EventListModule::class])
interface EventListComponent {
    fun inject(fragment: EventListFragment)
}

@Module
class EventListModule(
    val view: EventListFragmentView
) {
    @Provides
    @PerFragment
    fun providePresenter(
        filterManager: FilterManager,
        programEventDetailRepository: ProgramEventDetailRepository,
        preferences: PreferenceProvider,
        schedulers: SchedulerProvider
    ): EventListPresenter {
        return EventListPresenter(
            view,
            filterManager,
            programEventDetailRepository,
            preferences,
            schedulers
        )
    }
}
