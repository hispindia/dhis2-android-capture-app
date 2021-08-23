package org.medhis2yes.usescases.teiDashboard.nfcdata;

import org.medhis2yes.data.dagger.PerActivity;
import org.medhis2yes.data.qr.QRCodeGenerator;
import org.medhis2yes.data.qr.QRInterface;
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
