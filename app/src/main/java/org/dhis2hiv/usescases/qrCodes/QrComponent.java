package org.dhis2hiv.usescases.qrCodes;

import org.dhis2hiv.data.dagger.PerActivity;

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
