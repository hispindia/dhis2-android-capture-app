package org.dhis2hiv.usescases.settings.models

data class SettingsViewModel(
    val metadataSettingsViewModel: MetadataSettingsViewModel,
    val dataSettingsViewModel: DataSettingsViewModel,
    val syncParametersViewModel: SyncParametersViewModel,
    val reservedValueSettingsViewModel: ReservedValueSettingsViewModel,
    val smsSettingsViewModel: SMSSettingsViewModel
)
