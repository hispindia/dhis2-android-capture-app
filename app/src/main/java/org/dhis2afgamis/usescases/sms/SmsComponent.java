package org.dhis2afgamis.usescases.sms;

import org.dhis2afgamis.data.dagger.PerActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = SmsModule.class)
public interface SmsComponent {
    void inject(SmsSendingService sendingService);
}
