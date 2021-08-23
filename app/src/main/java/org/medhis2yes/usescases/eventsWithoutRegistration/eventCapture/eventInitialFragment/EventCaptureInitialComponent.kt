package org.medhis2yes.usescases.eventsWithoutRegistration.eventCapture.eventInitialFragment

import dagger.Subcomponent

@Subcomponent(modules = [EventCaptureInitialModule::class])
interface EventCaptureInitialComponent {
    fun inject(fragment: EventCaptureInitialFragment)
}
