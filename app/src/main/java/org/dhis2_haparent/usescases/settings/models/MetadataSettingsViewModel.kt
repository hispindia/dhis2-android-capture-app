package org.dhis2_haparent.usescases.settings.models

data class MetadataSettingsViewModel(
    val metadataSyncPeriod: Int,
    val lastMetadataSync: String,
    val hasErrors: Boolean,
    val canEdit: Boolean
)
