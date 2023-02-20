package org.dhis2_haparent.usescases.datasets.datasetDetail.datasetList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.dhis2_haparent.commons.filters.FilterManager
import org.dhis2_haparent.commons.matomo.MatomoAnalyticsController
import org.dhis2_haparent.commons.schedulers.SchedulerProvider
import org.dhis2_haparent.usescases.datasets.datasetDetail.DataSetDetailRepository

@Suppress("UNCHECKED_CAST")
class DataSetListViewModelFactory(
    val dataSetDetailRepository: DataSetDetailRepository,
    val schedulerProvider: SchedulerProvider,
    val filterManager: FilterManager,
    val matomoAnalyticsController: MatomoAnalyticsController
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DataSetListViewModel(
            dataSetDetailRepository,
            schedulerProvider,
            filterManager,
            matomoAnalyticsController
        ) as T
    }
}
