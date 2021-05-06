package org.fpandhis2.usescases.eventsWithoutRegistration.eventCapture;

import org.fpandhis2.data.dagger.PerActivity;
import org.fpandhis2.usescases.eventsWithoutRegistration.eventCapture.EventCaptureFragment.EventCaptureFormComponent;
import org.fpandhis2.usescases.eventsWithoutRegistration.eventCapture.EventCaptureFragment.EventCaptureFormModule;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = EventCaptureModule.class)
public interface EventCaptureComponent {
    void inject(EventCaptureActivity activity);

    EventCaptureFormComponent plus(EventCaptureFormModule formModule);
}
