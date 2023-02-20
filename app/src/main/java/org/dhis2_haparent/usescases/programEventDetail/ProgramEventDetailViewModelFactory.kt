package org.dhis2_haparent.usescases.programEventDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.dhis2_haparent.maps.usecases.MapStyleConfiguration

class ProgramEventDetailViewModelFactory(
    private val mapStyleConfiguration: MapStyleConfiguration
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProgramEventDetailViewModel(mapStyleConfiguration) as T
    }
}
