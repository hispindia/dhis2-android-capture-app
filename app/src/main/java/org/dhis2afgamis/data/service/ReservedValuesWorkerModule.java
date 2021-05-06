package org.dhis2afgamis.data.service;

import android.app.NotificationManager;
import android.content.Context;

import androidx.annotation.NonNull;

import org.dhis2afgamis.data.dagger.PerService;
import org.dhis2afgamis.data.prefs.PreferenceProvider;
import org.dhis2afgamis.data.service.workManager.WorkManagerController;
import org.dhis2afgamis.utils.analytics.AnalyticsHelper;
import org.hisp.dhis.android.core.D2;

import dagger.Module;
import dagger.Provides;

@Module
@PerService
public class ReservedValuesWorkerModule {

    @Provides
    @PerService
    NotificationManager notificationManager(@NonNull Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Provides
    @PerService
    SyncPresenter syncPresenter(
            @NonNull D2 d2,
            @NonNull PreferenceProvider preferences,
            @NonNull WorkManagerController workManagerController,
            @NonNull AnalyticsHelper analyticsHelper
    ) {
        return new SyncPresenterImpl(d2, preferences, workManagerController, analyticsHelper);
    }
}
