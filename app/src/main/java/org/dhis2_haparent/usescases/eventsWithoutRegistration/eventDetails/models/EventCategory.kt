package org.dhis2_haparent.usescases.eventsWithoutRegistration.eventDetails.models

import org.hisp.dhis.android.core.category.CategoryOption

data class EventCategory(
    val uid: String,
    val name: String,
    val optionsSize: Int,
    val options: List<CategoryOption>
)
