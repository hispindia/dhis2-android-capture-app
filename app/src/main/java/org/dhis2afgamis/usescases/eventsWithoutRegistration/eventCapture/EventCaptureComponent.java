package org.dhis2afgamis.usescases.eventsWithoutRegistration.eventCapture;

import org.dhis2afgamis.data.dagger.PerActivity;
import org.dhis2afgamis.usescases.eventsWithoutRegistration.eventCapture.EventCaptureFragment.EventCaptureFormComponent;
import org.dhis2afgamis.usescases.eventsWithoutRegistration.eventCapture.EventCaptureFragment.EventCaptureFormModule;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = EventCaptureModule.class)
public interface EventCaptureComponent {
    void inject(EventCaptureActivity activity);

    EventCaptureFormComponent plus(EventCaptureFormModule formModule);
}
