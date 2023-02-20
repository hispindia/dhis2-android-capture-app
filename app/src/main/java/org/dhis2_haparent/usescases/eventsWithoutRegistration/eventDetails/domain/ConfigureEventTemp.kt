package org.dhis2_haparent.usescases.eventsWithoutRegistration.eventDetails.domain

import org.dhis2_haparent.commons.data.EventCreationType
import org.dhis2_haparent.commons.data.EventCreationType.REFERAL
import org.dhis2_haparent.usescases.eventsWithoutRegistration.eventDetails.models.EventTemp
import org.dhis2_haparent.usescases.eventsWithoutRegistration.eventDetails.models.EventTempStatus

class ConfigureEventTemp(
    private val creationType: EventCreationType
) {

    operator fun invoke(status: EventTempStatus? = null): EventTemp {
        return EventTemp(
            active = isActive(),
            status = status,
            completed = isCompleted(status)
        )
    }

    private fun isCompleted(status: EventTempStatus?) = when (creationType) {
        REFERAL -> status != null
        else -> true
    }

    private fun isActive(): Boolean {
        return creationType == REFERAL
    }
}
