package org.dhis2hiv.usescases.eventsWithoutRegistration.eventCapture.EventCaptureFragment

import dagger.Subcomponent
import org.dhis2hiv.data.dagger.PerFragment

@PerFragment
@Subcomponent(modules = [EventCaptureFormModule::class])
interface EventCaptureFormComponent {
    fun inject(fragment: EventCaptureFormFragment)
}
