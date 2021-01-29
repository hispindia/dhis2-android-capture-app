package org.dhis2hiv.usescases.settings.models

data class ReservedValueSettingsViewModel(
    val numberOfReservedValuesToDownload: Int,
    val canBeEdited: Boolean
)
