package org.dhis2_haparent.usescases.settings

import java.io.File
import org.dhis2_haparent.commons.filters.FilterManager
import org.dhis2_haparent.commons.prefs.PreferenceProvider
import org.dhis2_haparent.data.service.workManager.WorkManagerController

class DeleteUserData(
    private val workManagerController: WorkManagerController,
    private val filterManager: FilterManager,
    private val preferencesProvider: PreferenceProvider
) {

    fun wipeCacheAndPreferences(file: File?) {
        filterManager.clearAllFilters()
        workManagerController.cancelAllWork()
        workManagerController.pruneWork()
        if (file != null) {
            deleteCache(file)
        }
        preferencesProvider.clear()
    }
}
