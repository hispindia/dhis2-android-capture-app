package org.dhis2afgamis.usescases.eventsWithoutRegistration.eventCapture.EventCaptureFragment

import dagger.Subcomponent
import org.dhis2afgamis.data.dagger.PerFragment

@PerFragment
@Subcomponent(modules = [EventCaptureFormModule::class])
interface EventCaptureFormComponent {
    fun inject(fragment: EventCaptureFormFragment)
}
