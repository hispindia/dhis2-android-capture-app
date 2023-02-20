package org.dhis2_haparent.usescases.datasets.datasetDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.dhis2_haparent.form.model.DispatcherProvider

@Suppress("UNCHECKED_CAST")
class DataSetDetailViewModelFactory(
    private val dispatcherProvider: DispatcherProvider,
    private val dataSetPageConfigurator: DataSetPageConfigurator
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DataSetDetailViewModel(
            dispatcherProvider,
            dataSetPageConfigurator
        ) as T
    }
}
