package org.dhis2_haparent.data.service;

import android.app.NotificationManager;
import android.content.Context;

import androidx.annotation.NonNull;

import org.dhis2_haparent.commons.di.dagger.PerService;
import org.dhis2_haparent.commons.prefs.PreferenceProvider;
import org.dhis2_haparent.data.service.workManager.WorkManagerController;
import org.dhis2_haparent.utils.analytics.AnalyticsHelper;
import org.hisp.dhis.android.core.D2;

import dagger.Module;
import dagger.Provides;

@Module
public class ReservedValuesWorkerModule {

    @Provides
    @PerService
    NotificationManager notificationManager(@NonNull Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Provides
    @PerService
    SyncRepository syncRepository(@NonNull D2 d2) {
        return new SyncRepositoryImpl(d2);
    }

    @Provides
    @PerService
    SyncPresenter syncPresenter(
            @NonNull D2 d2,
            @NonNull PreferenceProvider preferences,
            @NonNull WorkManagerController workManagerController,
            @NonNull AnalyticsHelper analyticsHelper,
            @NonNull SyncStatusController syncStatusController,
            @NonNull SyncRepository syncRepository
    ) {
        return new SyncPresenterImpl(d2, preferences, workManagerController, analyticsHelper, syncStatusController, syncRepository);
    }
}
