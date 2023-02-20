package org.dhis2_haparent.usescases.teiDashboard.nfcdata;

import org.dhis2_haparent.commons.di.dagger.PerActivity;

import dagger.Subcomponent;

/**
 * QUADRAM. Created by ppajuelo on 19/12/2017.
 */
@PerActivity
@Subcomponent(modules = NfcDataWriteModule.class)
public interface NfcDataWriteComponent {
    void inject(NfcDataWriteActivity activity);
}
