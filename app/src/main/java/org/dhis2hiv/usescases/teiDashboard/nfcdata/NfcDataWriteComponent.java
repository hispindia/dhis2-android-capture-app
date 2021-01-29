package org.dhis2hiv.usescases.teiDashboard.nfcdata;

import org.dhis2hiv.data.dagger.PerActivity;

import dagger.Subcomponent;

/**
 * QUADRAM. Created by ppajuelo on 19/12/2017.
 */
@PerActivity
@Subcomponent(modules = NfcDataWriteModule.class)
public interface NfcDataWriteComponent {
    void inject(NfcDataWriteActivity activity);
}
