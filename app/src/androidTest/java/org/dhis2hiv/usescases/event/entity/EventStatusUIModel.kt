package org.dhis2hiv.usescases.event.entity

data class EventStatusUIModel (
    val name: String,
    val status: String,
    val date: String,
    val orgUnit: String
)