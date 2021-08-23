package org.medhis2yes.usescases.eventsWithoutRegistration.eventCapture.eventCaptureFragment

import dagger.Subcomponent
import org.medhis2yes.data.dagger.PerFragment

@PerFragment
@Subcomponent(modules = [EventCaptureFormModule::class])
interface EventCaptureFormComponent {
    fun inject(fragment: EventCaptureFormFragment)
}
