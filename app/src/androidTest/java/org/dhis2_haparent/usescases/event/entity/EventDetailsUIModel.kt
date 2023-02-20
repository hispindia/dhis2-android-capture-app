package org.dhis2_haparent.usescases.event.entity

data class EventDetailsUIModel (
    val programStage: String,
    val completedPercentage: Int,
    val eventDate: String,
    val orgUnit: String
)