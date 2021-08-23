package org.medhis2yes.usescases.teiDashboard.dashboardfragments.indicators

import io.reactivex.disposables.CompositeDisposable
import org.medhis2yes.data.schedulers.SchedulerProvider
import org.medhis2yes.data.schedulers.defaultSubscribe
import timber.log.Timber

class IndicatorsPresenter(
    val schedulerProvider: SchedulerProvider,
    val view: IndicatorsView,
    val indicatorRepository: IndicatorRepository
) {

    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun init() {
        compositeDisposable.add(
            indicatorRepository.fetchData()
                .defaultSubscribe(schedulerProvider, { view.swapAnalytics(it) }, { Timber.d(it) })
        )
    }

    fun onDettach() = compositeDisposable.clear()

    fun displayMessage(message: String) = view.displayMessage(message)
}
