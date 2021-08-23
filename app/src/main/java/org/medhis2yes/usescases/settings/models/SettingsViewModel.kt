package org.medhis2yes.usescases.settings.models

data class SettingsViewModel(
    val metadataSettingsViewModel: MetadataSettingsViewModel,
    val dataSettingsViewModel: DataSettingsViewModel,
    val syncParametersViewModel: SyncParametersViewModel,
    val reservedValueSettingsViewModel: ReservedValueSettingsViewModel,
    val smsSettingsViewModel: SMSSettingsViewModel
)
