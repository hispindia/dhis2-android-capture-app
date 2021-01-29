package org.dhis2hiv.usescases.teiDashboard.nfcdata;

import org.dhis2hiv.data.dagger.PerActivity;
import org.dhis2hiv.data.qr.QRCodeGenerator;
import org.dhis2hiv.data.qr.QRInterface;
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
