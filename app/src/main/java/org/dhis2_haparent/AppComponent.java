package org.dhis2_haparent;

import org.dhis2_haparent.commons.featureconfig.di.FeatureConfigModule;
import org.dhis2_haparent.commons.network.NetworkUtils;
import org.dhis2_haparent.commons.network.NetworkUtilsModule;
import org.dhis2_haparent.data.dispatcher.DispatcherModule;
import org.dhis2_haparent.data.forms.dataentry.validation.ValidatorModule;
import org.dhis2_haparent.commons.locationprovider.LocationModule;
import org.dhis2_haparent.commons.locationprovider.LocationProvider;
import org.dhis2_haparent.commons.prefs.PreferenceModule;
import org.dhis2_haparent.commons.prefs.PreferenceProvider;
import org.dhis2_haparent.commons.schedulers.SchedulerModule;
import org.dhis2_haparent.data.server.ServerComponent;
import org.dhis2_haparent.data.server.ServerModule;
import org.dhis2_haparent.data.service.workManager.WorkManagerController;
import org.dhis2_haparent.data.service.workManager.WorkManagerModule;
import org.dhis2_haparent.usescases.login.LoginComponent;
import org.dhis2_haparent.usescases.login.LoginModule;
import org.dhis2_haparent.usescases.splash.SplashComponent;
import org.dhis2_haparent.usescases.splash.SplashModule;
import org.dhis2_haparent.utils.Validator;
import org.dhis2_haparent.utils.analytics.AnalyticsModule;
import org.dhis2_haparent.commons.matomo.MatomoAnalyticsController;
import org.dhis2_haparent.utils.analytics.matomo.MatomoAnalyticsModule;
import org.dhis2_haparent.commons.filters.di.FilterModule;
import org.dhis2_haparent.commons.reporting.CrashReportController;
import org.dhis2_haparent.commons.reporting.CrashReportModule;
import org.hisp.dhis.android.core.common.ValueType;

import java.util.Map;

import javax.inject.Singleton;

import dagger.Component;
import dispatch.core.DispatcherProvider;

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
        FilterModule.class,
        DispatcherModule.class,
        FeatureConfigModule.class,
        NetworkUtilsModule.class,
        CustomDispatcherModule.class
})
public  interface AppComponent {

    @Component.Builder
    interface Builder {
        Builder appModule(AppModule appModule);

        Builder schedulerModule(SchedulerModule schedulerModule);

        Builder analyticsModule(AnalyticsModule module);

        Builder preferenceModule(PreferenceModule preferenceModule);

        Builder workManagerController(WorkManagerModule workManagerModule);

        Builder crashReportModule(CrashReportModule crashReportModule);

        Builder coroutineDispatchers(DispatcherModule dispatcherModule);

        Builder featureConfigModule(FeatureConfigModule featureConfigModule);

        Builder networkUtilsModule(NetworkUtilsModule networkUtilsModule);

        Builder customDispatcher(CustomDispatcherModule dispatcherProvider);

        AppComponent build();
    }

    Map<ValueType, Validator> injectValidators();

    CrashReportController injectCrashReportController();

    PreferenceProvider preferenceProvider();

    WorkManagerController workManagerController();

    MatomoAnalyticsController matomoController();

    org.dhis2_haparent.form.model.DispatcherProvider dispatcherProvider();

    LocationProvider locationProvider();

    NetworkUtils networkUtilsProvider();

    DispatcherProvider customDispatcherProvider();

    //injection targets
    void inject(App app);

    //sub-components
    ServerComponent plus(ServerModule serverModule);

    SplashComponent plus(SplashModule module);

    LoginComponent plus(LoginModule loginContractsModule);
}
