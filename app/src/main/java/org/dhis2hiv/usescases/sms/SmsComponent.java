package org.dhis2hiv.usescases.sms;

import org.dhis2hiv.data.dagger.PerActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = SmsModule.class)
public interface SmsComponent {
    void inject(SmsSendingService sendingService);
}
