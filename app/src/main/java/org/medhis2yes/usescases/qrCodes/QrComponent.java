package org.medhis2yes.usescases.qrCodes;

import org.medhis2yes.data.dagger.PerActivity;

import dagger.Subcomponent;

/**
 * Created by ppajuelo on 30/11/2017.
 *
 */
@PerActivity
@Subcomponent(modules = QrModule.class)
public interface QrComponent {
    void inject(QrActivity qrActivity);
}
