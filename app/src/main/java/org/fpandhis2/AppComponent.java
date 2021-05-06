package org.fpandhis2;

import org.fpandhis2.data.prefs.PreferenceModule;
import org.fpandhis2.data.prefs.PreferenceProvider;
import org.fpandhis2.data.schedulers.SchedulerModule;
import org.fpandhis2.data.server.ServerComponent;
import org.fpandhis2.data.server.ServerModule;
import org.fpandhis2.data.service.workManager.WorkManagerController;
import org.fpandhis2.data.service.workManager.WorkManagerModule;
import org.fpandhis2.usescases.login.LoginComponent;
import org.fpandhis2.usescases.login.LoginModule;
import org.fpandhis2.usescases.splash.SplashComponent;
import org.fpandhis2.usescases.splash.SplashModule;
import org.fpandhis2.utils.analytics.AnalyticsModule;
import org.fpandhis2.utils.analytics.matomo.MatomoAnalyticsModule;
import org.fpandhis2.utils.session.PinModule;
import org.fpandhis2.utils.session.SessionComponent;

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
    WorkManagerController workManagerController();
    //injection targets
    void inject(App app);

    //sub-components
    ServerComponent plus(ServerModule serverModule);

    SplashComponent plus(SplashModule module);

    LoginComponent plus(LoginModule loginContractsModule);

    SessionComponent plus(PinModule pinModule);
}
