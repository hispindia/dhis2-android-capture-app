package org.dhis2_haparent.usescases.eventsWithoutRegistration.eventDetails.injection

import dagger.Subcomponent
import org.dhis2_haparent.commons.di.dagger.PerFragment
import org.dhis2_haparent.usescases.eventsWithoutRegistration.eventDetails.ui.EventDetailsFragment

@PerFragment
@Subcomponent(modules = [EventDetailsModule::class])
interface EventDetailsComponent {
    fun inject(eventDetailsFragment: EventDetailsFragment?)
}
