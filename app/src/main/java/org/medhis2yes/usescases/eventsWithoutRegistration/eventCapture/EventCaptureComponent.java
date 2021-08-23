package org.medhis2yes.usescases.eventsWithoutRegistration.eventCapture;

import org.medhis2yes.data.dagger.PerActivity;
import org.medhis2yes.usescases.eventsWithoutRegistration.eventCapture.eventCaptureFragment.EventCaptureFormComponent;
import org.medhis2yes.usescases.eventsWithoutRegistration.eventCapture.eventCaptureFragment.EventCaptureFormModule;
import org.medhis2yes.usescases.teiDashboard.dashboardfragments.indicators.IndicatorsComponent;
import org.medhis2yes.usescases.teiDashboard.dashboardfragments.indicators.IndicatorsModule;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = EventCaptureModule.class)
public interface EventCaptureComponent {
    void inject(EventCaptureActivity activity);

    EventCaptureFormComponent plus(EventCaptureFormModule formModule);

    IndicatorsComponent plus(IndicatorsModule indicatorsModule);
}
