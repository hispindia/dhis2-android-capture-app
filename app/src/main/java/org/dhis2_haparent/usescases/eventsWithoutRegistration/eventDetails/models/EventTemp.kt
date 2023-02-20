package org.dhis2_haparent.usescases.eventsWithoutRegistration.eventDetails.models

data class EventTemp(
    val active: Boolean = false,
    val status: EventTempStatus? = null,
    val completed: Boolean = true
)
