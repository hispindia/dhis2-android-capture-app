package org.dhis2_haparent.usescases.eventsWithoutRegistration.eventCapture.eventCaptureFragment

import dagger.Subcomponent
import org.dhis2_haparent.commons.di.dagger.PerFragment

@PerFragment
@Subcomponent(modules = [EventCaptureFormModule::class])
interface EventCaptureFormComponent {
    fun inject(fragment: EventCaptureFormFragment)
}
