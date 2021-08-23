package org.medhis2yes.usescases.programEventDetail.eventList

import io.reactivex.disposables.CompositeDisposable
import org.medhis2yes.data.prefs.PreferenceProvider
import org.medhis2yes.data.schedulers.SchedulerProvider
import org.medhis2yes.data.schedulers.defaultSubscribe
import org.medhis2yes.usescases.programEventDetail.ProgramEventDetailRepository
import org.medhis2yes.utils.filters.FilterManager
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
        return eventRepository.program().blockingFirst()
    }
}
