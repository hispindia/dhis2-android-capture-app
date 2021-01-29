package org.dhis2hiv.usescases.qrCodes.eventsworegistration;

import org.dhis2hiv.data.dagger.PerActivity;

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
