package org.dhis2_haparent.usescases.settings.models

import org.dhis2_haparent.data.service.SyncResult

data class DataSettingsViewModel(
    val dataSyncPeriod: Int,
    val lastDataSync: String,
    val syncHasErrors: Boolean,
    val dataHasErrors: Boolean,
    val dataHasWarnings: Boolean,
    val canEdit: Boolean,
    val syncResult: SyncResult? = null
)
