package org.fpandhis2.usescases.qrCodes.eventsworegistration;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;

import org.fpandhis2.data.qr.QRInterface;
import org.fpandhis2.data.schedulers.SchedulerProvider;

import timber.log.Timber;

public class QrEventsWORegistrationPresenter implements QrEventsWORegistrationContracts.Presenter {

    private final QRInterface qrInterface;
    private final SchedulerProvider schedulerProvider;
    private QrEventsWORegistrationContracts.View view;

    QrEventsWORegistrationPresenter(QRInterface qrInterface, SchedulerProvider schedulerProvider) {
        this.qrInterface = qrInterface;
        this.schedulerProvider = schedulerProvider;
    }

    @SuppressLint({"RxLeakedSubscription", "CheckResult"})
    public void generateQrs(@NonNull String eventUid, @NonNull QrEventsWORegistrationContracts.View view) {
        this.view = view;
        qrInterface.eventWORegistrationQRs(eventUid)
                .subscribeOn(schedulerProvider.computation())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        view::showQR,
                        Timber::d
                );
    }

    @Override
    public void onBackClick() {
        if (view != null)
            view.onBackClick();
    }

    @Override
    public void onPrevQr() {
        view.onPrevQr();
    }

    @Override
    public void onNextQr() {
        view.onNextQr();
    }

}
