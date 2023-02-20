package org.dhis2_haparent.usescases.settings.models

data class ReservedValueSettingsViewModel(
    val numberOfReservedValuesToDownload: Int,
    val canBeEdited: Boolean
)
