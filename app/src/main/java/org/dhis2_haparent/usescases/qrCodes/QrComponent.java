package org.dhis2_haparent.usescases.qrCodes;

import org.dhis2_haparent.commons.di.dagger.PerActivity;

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
