package org.medhis2yes.usescases.settings.models

data class DataSettingsViewModel(
    val dataSyncPeriod: Int,
    val lastDataSync: String,
    val syncHasErrors: Boolean,
    val dataHasErrors: Boolean,
    val dataHasWarnings: Boolean,
    val canEdit: Boolean
)
