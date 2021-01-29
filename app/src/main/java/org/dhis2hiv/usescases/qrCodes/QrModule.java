package org.dhis2hiv.usescases.qrCodes;

import org.dhis2hiv.data.dagger.PerActivity;
import org.dhis2hiv.data.qr.QRCodeGenerator;
import org.dhis2hiv.data.qr.QRInterface;
import org.dhis2hiv.data.schedulers.SchedulerProvider;
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
