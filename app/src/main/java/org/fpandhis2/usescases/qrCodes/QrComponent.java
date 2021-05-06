package org.fpandhis2.usescases.qrCodes;

import org.fpandhis2.data.dagger.PerActivity;

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
