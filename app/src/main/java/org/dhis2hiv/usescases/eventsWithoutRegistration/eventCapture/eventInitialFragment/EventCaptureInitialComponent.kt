package org.dhis2hiv.usescases.eventsWithoutRegistration.eventCapture.eventInitialFragment

import dagger.Subcomponent

@Subcomponent(modules = [EventCaptureInitialModule::class])
interface EventCaptureInitialComponent {
    fun inject(fragment: EventCaptureInitialFragment)
}
