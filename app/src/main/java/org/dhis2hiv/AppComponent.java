package org.dhis2hiv;

import org.dhis2hiv.data.prefs.PreferenceModule;
import org.dhis2hiv.data.prefs.PreferenceProvider;
import org.dhis2hiv.data.schedulers.SchedulerModule;
import org.dhis2hiv.data.server.ServerComponent;
import org.dhis2hiv.data.server.ServerModule;
import org.dhis2hiv.data.service.workManager.WorkManagerModule;
import org.dhis2hiv.usescases.login.LoginComponent;
import org.dhis2hiv.usescases.login.LoginModule;
import org.dhis2hiv.usescases.splash.SplashComponent;
import org.dhis2hiv.usescases.splash.SplashModule;
import org.dhis2hiv.utils.analytics.AnalyticsModule;
import org.dhis2hiv.utils.analytics.matomo.MatomoAnalyticsModule;
import org.dhis2hiv.utils.session.PinModule;
import org.dhis2hiv.utils.session.SessionComponent;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ppajuelo on 10/10/2017.
 */
@Singleton
@Component(modules = {
        AppModule.class, SchedulerModule.class, AnalyticsModule.class, PreferenceModule.class, WorkManagerModule.class,
        MatomoAnalyticsModule.class
})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        Builder appModule(AppModule appModule);

        Builder schedulerModule(SchedulerModule schedulerModule);

        Builder analyticsModule(AnalyticsModule module);

        Builder preferenceModule(PreferenceModule preferenceModule);

        Builder workManagerController(WorkManagerModule workManagerModule);

        AppComponent build();
    }

    PreferenceProvider preferenceProvider();

    //injection targets
    void inject(App app);

    //sub-components
    ServerComponent plus(ServerModule serverModule);

    SplashComponent plus(SplashModule module);

    LoginComponent plus(LoginModule loginContractsModule);

    SessionComponent plus(PinModule pinModule);
}
