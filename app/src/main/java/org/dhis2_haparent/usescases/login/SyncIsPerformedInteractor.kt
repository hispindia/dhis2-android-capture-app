package org.dhis2_haparent.usescases.login

import org.dhis2_haparent.data.server.UserManager
import org.dhis2_haparent.usescases.sync.WAS_INITIAL_SYNC_DONE

class SyncIsPerformedInteractor(private val userManager: UserManager?) {
    fun execute(): Boolean {
        if (userManager == null) return false

        val entryExists = userManager.d2.dataStoreModule().localDataStore().value(
            WAS_INITIAL_SYNC_DONE
        ).blockingExists()
        val isInitialSyncDone = if (entryExists) {
            val entry = userManager.d2.dataStoreModule().localDataStore().value(
                WAS_INITIAL_SYNC_DONE
            ).blockingGet()
            !entry.value().isNullOrEmpty() && entry.value() == "True"
        } else {
            false
        }
        return isInitialSyncDone
    }
}
