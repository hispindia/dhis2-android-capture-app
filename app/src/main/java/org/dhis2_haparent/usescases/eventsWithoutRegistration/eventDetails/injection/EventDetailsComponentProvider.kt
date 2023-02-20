package org.dhis2_haparent.usescases.eventsWithoutRegistration.eventDetails.injection

interface EventDetailsComponentProvider {
    fun provideEventDetailsComponent(
        module: EventDetailsModule?
    ): EventDetailsComponent?
}
