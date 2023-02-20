package org.dhis2_haparent.usescases.searchTrackEntity

import kotlinx.coroutines.Dispatchers
import org.dhis2_haparent.form.model.DispatcherProvider

class SearchDispatchers : DispatcherProvider {
    override fun io() = Dispatchers.IO

    override fun computation() = Dispatchers.Default

    override fun ui() = Dispatchers.Main
}
