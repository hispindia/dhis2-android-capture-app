package org.dhis2_haparent.usescases.searchTrackEntity.ui

sealed class SearchUIData
data class UnableToSearchOutsideData(
    val trackedEntityTypeAttributes: List<String>,
    val trackedEntityTypeName: String
) : SearchUIData()
