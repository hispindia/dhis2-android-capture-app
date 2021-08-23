package org.medhis2yes.usescases.qrCodes;

import org.medhis2yes.data.dagger.PerActivity;
import org.medhis2yes.data.qr.QRCodeGenerator;
import org.medhis2yes.data.qr.QRInterface;
import org.medhis2yes.data.schedulers.SchedulerProvider;
import org.hisp.dhis.android.core.D2;

import dagger.Module;
import dagger.Provides;

/**
 * QUADRAM. Created by ppajuelo on 30/11/2017.
 */
@PerActivity
@Module
public class QrModule {
    @Provides
    @PerActivity
    QrContracts.View provideView(QrActivity qrActivity) {
        return qrActivity;
    }

    @Provides
    @PerActivity
    QrContracts.Presenter providePresenter(QRInterface qrInterface, SchedulerProvider schedulerProvider) {
        return new QrPresenter(qrInterface, schedulerProvider);
    }

    @Provides
    @PerActivity
    QRInterface providesQRInterface(D2 d2) {
        return new QRCodeGenerator(d2);
    }
}
