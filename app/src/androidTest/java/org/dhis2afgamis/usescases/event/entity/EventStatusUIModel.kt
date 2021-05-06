package org.dhis2afgamis.usescases.event.entity

data class EventStatusUIModel (
    val name: String,
    val status: String,
    val date: String,
    val orgUnit: String
)