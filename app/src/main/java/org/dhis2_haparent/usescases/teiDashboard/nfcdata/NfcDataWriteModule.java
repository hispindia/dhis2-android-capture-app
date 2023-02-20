package org.dhis2_haparent.usescases.teiDashboard.nfcdata;

import org.dhis2_haparent.commons.di.dagger.PerActivity;
import org.dhis2_haparent.data.qr.QRCodeGenerator;
import org.dhis2_haparent.data.qr.QRInterface;
import org.hisp.dhis.android.core.D2;

import dagger.Module;
import dagger.Provides;

/**
 * QUADRAM. Created by ppajuelo on 19/12/2017.
 */

@Module
public class NfcDataWriteModule {

    @Provides
    @PerActivity
    QRInterface providesQRInterface(D2 d2) {
        return new QRCodeGenerator(d2);
    }
}
