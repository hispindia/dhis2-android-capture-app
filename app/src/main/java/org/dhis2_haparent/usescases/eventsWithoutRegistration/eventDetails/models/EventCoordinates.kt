package org.dhis2_haparent.usescases.eventsWithoutRegistration.eventDetails.models

import org.dhis2_haparent.form.model.FieldUiModel

data class EventCoordinates(
    val active: Boolean = true,
    val model: FieldUiModel? = null
)
