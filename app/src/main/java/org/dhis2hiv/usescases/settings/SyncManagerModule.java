package org.dhis2hiv.usescases.settings;

import org.dhis2hiv.R;
import org.dhis2hiv.data.dagger.PerFragment;
import org.dhis2hiv.data.prefs.PreferenceProvider;
import org.dhis2hiv.data.schedulers.SchedulerProvider;
import org.dhis2hiv.data.service.workManager.WorkManagerController;
import org.dhis2hiv.utils.analytics.AnalyticsHelper;
import org.dhis2hiv.usescases.settings.models.ErrorModelMapper;
import org.hisp.dhis.android.core.D2;

import dagger.Module;
import dagger.Provides;

@Module
public final class SyncManagerModule {

    private final SyncManagerContracts.View view;

    SyncManagerModule(SyncManagerContracts.View view){
        this.view = view;
    }

    @Provides
    @PerFragment
    SyncManagerContracts.Presenter providePresenter(
            D2 d2,
            SchedulerProvider schedulerProvider,
            GatewayValidator gatewayValidator,
            PreferenceProvider preferenceProvider,
            WorkManagerController workManagerController,
            SettingsRepository settingsRepository,
            AnalyticsHelper analyticsHelper) {
        return new SyncManagerPresenter(d2,
                schedulerProvider,
                gatewayValidator,
                preferenceProvider,
                workManagerController,
                settingsRepository,
                view,
                analyticsHelper,
                new ErrorModelMapper(view.getContext().getString(R.string.fk_message)));
    }

    @Provides
    @PerFragment
    SettingsRepository provideRepository(
            D2 d2,
            PreferenceProvider preferenceProvider
    ) {
        return new SettingsRepository(d2,
                preferenceProvider);
    }

    @Provides
    @PerFragment
    GatewayValidator providesGatewayValidator() {
        return new GatewayValidator();
    }
}
