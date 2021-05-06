package org.dhis2afgamis.usescases.qrCodes.eventsworegistration;

import org.dhis2afgamis.data.dagger.PerActivity;

import dagger.Subcomponent;

/**
 * Created by ppajuelo on 30/11/2017.
 *
 */
@PerActivity
@Subcomponent(modules = QrEventsWORegistrationModule.class)
public interface QrEventsWORegistrationComponent {
    void inject(QrEventsWORegistrationActivity qrActivity);
}
