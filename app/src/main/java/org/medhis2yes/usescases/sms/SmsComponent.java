package org.medhis2yes.usescases.sms;

import org.medhis2yes.data.dagger.PerActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = SmsModule.class)
public interface SmsComponent {
    void inject(SmsSendingService sendingService);
}
