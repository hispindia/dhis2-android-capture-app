package org.medhis2yes.usescases.settings;


import org.medhis2yes.R;
import org.medhis2yes.data.dagger.PerFragment;
import org.medhis2yes.data.prefs.PreferenceProvider;
import org.medhis2yes.data.schedulers.SchedulerProvider;
import org.medhis2yes.data.server.ServerComponent;
import org.medhis2yes.data.server.UserManager;
import org.medhis2yes.data.service.workManager.WorkManagerController;
import org.medhis2yes.utils.analytics.AnalyticsHelper;
import org.medhis2yes.usescases.settings.models.ErrorModelMapper;
import org.medhis2yes.utils.analytics.matomo.MatomoAnalyticsController;
import org.hisp.dhis.android.core.D2;

import dagger.Module;
import dagger.Provides;

@Module
public final class SyncManagerModule {

    private final SyncManagerContracts.View view;
    private final UserManager userManager;

    SyncManagerModule(SyncManagerContracts.View view, ServerComponent serverComponent){
        this.view = view;
        this.userManager = serverComponent.userManager();
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
            AnalyticsHelper analyticsHelper,
            MatomoAnalyticsController matomoAnalyticsController) {
        return new SyncManagerPresenter(d2,
                schedulerProvider,
                gatewayValidator,
                preferenceProvider,
                workManagerController,
                settingsRepository,
                userManager,
                view,
                analyticsHelper,
                new ErrorModelMapper(view.getContext().getString(R.string.fk_message)),
                matomoAnalyticsController);
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
