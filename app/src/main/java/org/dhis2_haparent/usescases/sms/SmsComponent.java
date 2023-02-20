package org.dhis2_haparent.usescases.sms;

import org.dhis2_haparent.commons.di.dagger.PerActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = SmsModule.class)
public interface SmsComponent {
    void inject(SmsSendingService sendingService);
}
