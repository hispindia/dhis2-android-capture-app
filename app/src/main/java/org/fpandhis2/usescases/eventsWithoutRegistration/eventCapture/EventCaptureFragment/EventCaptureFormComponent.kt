package org.fpandhis2.usescases.eventsWithoutRegistration.eventCapture.EventCaptureFragment

import dagger.Subcomponent
import org.fpandhis2.data.dagger.PerFragment

@PerFragment
@Subcomponent(modules = [EventCaptureFormModule::class])
interface EventCaptureFormComponent {
    fun inject(fragment: EventCaptureFormFragment)
}
