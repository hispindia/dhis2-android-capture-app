package org.dhis2_haparent.usescases.programEventDetail.eventList

import io.reactivex.disposables.CompositeDisposable
import org.dhis2_haparent.commons.filters.FilterManager
import org.dhis2_haparent.commons.prefs.PreferenceProvider
import org.dhis2_haparent.commons.schedulers.SchedulerProvider
import org.dhis2_haparent.commons.schedulers.defaultSubscribe
import org.dhis2_haparent.usescases.programEventDetail.ProgramEventDetailRepository
import org.hisp.dhis.android.core.program.Program
import timber.log.Timber

class EventListPresenter(
    val view: EventListFragmentView,
    val filterManager: FilterManager,
    val eventRepository: ProgramEventDetailRepository,
    val preferences: PreferenceProvider,
    val schedulerProvider: SchedulerProvider
) {

    val disposable = CompositeDisposable()

    fun init() {
        disposable.add(
            filterManager.asFlowable().startWith(filterManager)
                .map { eventRepository.filteredProgramEvents() }
                .defaultSubscribe(
                    schedulerProvider,
                    { view.setLiveData(it) },
                    { Timber.e(it) }
                )
        )
    }

    fun program(): Program {
        return eventRepository.program().blockingGet()
    }
}
