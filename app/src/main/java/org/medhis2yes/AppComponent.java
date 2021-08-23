package org.medhis2yes;

import org.medhis2yes.data.forms.dataentry.validation.ValidatorModule;
import org.medhis2yes.data.location.LocationModule;
import org.medhis2yes.data.location.LocationProvider;
import org.medhis2yes.data.prefs.PreferenceModule;
import org.medhis2yes.data.prefs.PreferenceProvider;
import org.medhis2yes.data.schedulers.SchedulerModule;
import org.medhis2yes.data.server.ServerComponent;
import org.medhis2yes.data.server.ServerModule;
import org.medhis2yes.data.service.workManager.WorkManagerController;
import org.medhis2yes.data.service.workManager.WorkManagerModule;
import org.medhis2yes.usescases.login.LoginComponent;
import org.medhis2yes.usescases.login.LoginModule;
import org.medhis2yes.usescases.splash.SplashComponent;
import org.medhis2yes.usescases.splash.SplashModule;
import org.medhis2yes.utils.Validator;
import org.medhis2yes.utils.analytics.AnalyticsModule;
import org.medhis2yes.utils.analytics.matomo.MatomoAnalyticsController;
import org.medhis2yes.utils.analytics.matomo.MatomoAnalyticsModule;
import org.medhis2yes.utils.filters.FilterModule;
import org.medhis2yes.utils.reporting.CrashReportController;
import org.medhis2yes.utils.reporting.CrashReportModule;
import org.medhis2yes.utils.session.PinModule;
import org.medhis2yes.utils.session.SessionComponent;
import org.hisp.dhis.android.core.common.ValueType;

import java.util.Map;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ppajuelo on 10/10/2017.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        SchedulerModule.class,
        AnalyticsModule.class,
        PreferenceModule.class,
        WorkManagerModule.class,
        MatomoAnalyticsModule.class,
        ValidatorModule.class,
        CrashReportModule.class,
        LocationModule.class,
        FilterModule.class
})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        Builder appModule(AppModule appModule);

        Builder schedulerModule(SchedulerModule schedulerModule);

        Builder analyticsModule(AnalyticsModule module);

        Builder preferenceModule(PreferenceModule preferenceModule);

        Builder workManagerController(WorkManagerModule workManagerModule);

        Builder crashReportModule(CrashReportModule crashReportModule);

        AppComponent build();
    }

    Map<ValueType, Validator> injectValidators();

    CrashReportController injectCrashReportController();

    PreferenceProvider preferenceProvider();

    WorkManagerController workManagerController();

    MatomoAnalyticsController matomoController();

    LocationProvider locationProvider();

    //injection targets
    void inject(App app);

    //sub-components
    ServerComponent plus(ServerModule serverModule);

    SplashComponent plus(SplashModule module);

    LoginComponent plus(LoginModule loginContractsModule);

    SessionComponent plus(PinModule pinModule);
}
