package org.dhis2_haparent.usescases.searchTrackEntity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.dhis2_haparent.commons.network.NetworkUtils
import org.dhis2_haparent.form.model.DispatcherProvider
import org.dhis2_haparent.maps.usecases.MapStyleConfiguration

@Suppress("UNCHECKED_CAST")
class SearchTeiViewModelFactory(
    val presenter: SearchTEContractsModule.Presenter,
    val searchRepository: SearchRepository,
    private val searchNavPageConfigurator: SearchPageConfigurator,
    private val initialProgramUid: String?,
    private val initialQuery: MutableMap<String, String>?,
    private val mapDataRepository: MapDataRepository,
    private val networkUtils: NetworkUtils,
    private val dispatchers: DispatcherProvider,
    private val mapStyleConfig: MapStyleConfiguration
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchTEIViewModel(
            initialProgramUid,
            initialQuery,
            presenter,
            searchRepository,
            searchNavPageConfigurator,
            mapDataRepository,
            networkUtils,
            dispatchers,
            mapStyleConfig
        ) as T
    }
}
