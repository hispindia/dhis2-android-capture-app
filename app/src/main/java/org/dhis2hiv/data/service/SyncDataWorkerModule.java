package org.dhis2hiv.data.service;

import androidx.annotation.NonNull;

import org.dhis2hiv.data.dagger.PerService;
import org.dhis2hiv.data.prefs.PreferenceProvider;
import org.dhis2hiv.data.service.workManager.WorkManagerController;
import org.dhis2hiv.utils.analytics.AnalyticsHelper;
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
