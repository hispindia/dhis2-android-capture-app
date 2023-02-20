package org.dhis2_haparent.usescases.qrCodes.eventsworegistration;

import org.dhis2_haparent.commons.di.dagger.PerActivity;

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
