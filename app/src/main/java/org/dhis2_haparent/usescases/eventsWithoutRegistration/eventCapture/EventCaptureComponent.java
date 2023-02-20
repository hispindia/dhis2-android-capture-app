package org.dhis2_haparent.usescases.eventsWithoutRegistration.eventCapture;

import org.dhis2_haparent.commons.di.dagger.PerActivity;
import org.dhis2_haparent.usescases.eventsWithoutRegistration.eventCapture.eventCaptureFragment.EventCaptureFormComponent;
import org.dhis2_haparent.usescases.eventsWithoutRegistration.eventCapture.eventCaptureFragment.EventCaptureFormModule;
import org.dhis2_haparent.usescases.eventsWithoutRegistration.eventDetails.injection.EventDetailsComponent;
import org.dhis2_haparent.usescases.eventsWithoutRegistration.eventDetails.injection.EventDetailsModule;
import org.dhis2_haparent.usescases.teiDashboard.dashboardfragments.indicators.IndicatorsComponent;
import org.dhis2_haparent.usescases.teiDashboard.dashboardfragments.indicators.IndicatorsModule;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = EventCaptureModule.class)
public interface EventCaptureComponent {
    void inject(EventCaptureActivity activity);

    EventCaptureFormComponent plus(EventCaptureFormModule formModule);

    IndicatorsComponent plus(IndicatorsModule indicatorsModule);

    EventDetailsComponent plus(EventDetailsModule eventDetailsModule);
}
