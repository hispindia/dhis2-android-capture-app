package org.medhis2yes.usescases.event.entity

data class EventDetailsUIModel (
    val programStage: String,
    val completedPercentage: Int,
    val eventDate: String,
    val orgUnit: String
)