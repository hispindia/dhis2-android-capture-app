package org.fpandhis2.data.service;

import androidx.annotation.NonNull;

import org.fpandhis2.data.dagger.PerService;
import org.fpandhis2.data.prefs.PreferenceProvider;
import org.fpandhis2.data.service.workManager.WorkManagerController;
import org.fpandhis2.utils.analytics.AnalyticsHelper;
import org.hisp.dhis.android.core.D2;

import dagger.Module;
import dagger.Provides;

@Module
@PerService
public class SyncGranularRxModule {

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
