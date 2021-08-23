package org.medhis2yes.data.service;

import androidx.annotation.NonNull;

import org.medhis2yes.data.dagger.PerService;
import org.medhis2yes.data.prefs.PreferenceProvider;
import org.medhis2yes.data.service.workManager.WorkManagerController;
import org.medhis2yes.utils.analytics.AnalyticsHelper;
import org.hisp.dhis.android.core.D2;

import dagger.Module;
import dagger.Provides;

@Module
@PerService
public class SyncDataWorkerModule {

    @Provides
    @PerService
    SyncPresenter syncPresenter(
            @NonNull D2 d2,
            @NonNull PreferenceProvider preferences,
            @NonNull WorkManagerController workManagerController,
            @NonNull AnalyticsHelper analyticsHelper
            ) {
        return new SyncPresenterImpl(d2, preferences, workManagerController,analyticsHelper);
    }
}
