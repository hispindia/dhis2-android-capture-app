package org.medhis2yes;

import android.content.Context;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import org.medhis2yes.data.appinspector.AppInspector;
import org.medhis2yes.data.dagger.PerActivity;
import org.medhis2yes.data.dagger.PerServer;
import org.medhis2yes.data.dagger.PerUser;
import org.medhis2yes.data.prefs.Preference;
import org.medhis2yes.data.prefs.PreferenceModule;
import org.medhis2yes.data.schedulers.SchedulerModule;
import org.medhis2yes.data.schedulers.SchedulersProviderImpl;
import org.medhis2yes.data.server.ServerComponent;
import org.medhis2yes.data.server.ServerModule;
import org.medhis2yes.data.server.UserManager;
import org.medhis2yes.data.service.workManager.WorkManagerModule;
import org.medhis2yes.data.user.UserComponent;
import org.medhis2yes.data.user.UserModule;
import org.medhis2yes.uicomponents.map.MapController;
import org.medhis2yes.usescases.login.LoginComponent;
import org.medhis2yes.usescases.login.LoginContracts;
import org.medhis2yes.usescases.login.LoginModule;
import org.medhis2yes.usescases.teiDashboard.TeiDashboardComponent;
import org.medhis2yes.usescases.teiDashboard.TeiDashboardModule;
import org.medhis2yes.utils.analytics.AnalyticsModule;
import org.medhis2yes.utils.reporting.CrashReportModule;
import org.medhis2yes.utils.session.PinModule;
import org.medhis2yes.utils.session.SessionComponent;
import org.medhis2yes.utils.timber.DebugTree;
import org.medhis2yes.utils.timber.ReleaseTree;
import org.hisp.dhis.android.core.D2;
import org.hisp.dhis.android.core.D2Manager;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.SocketException;

import javax.inject.Singleton;

import cat.ereza.customactivityoncrash.config.CaocConfig;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.plugins.RxJavaPlugins;
import timber.log.Timber;

public class App extends MultiDexApplication implements Components, LifecycleObserver {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @NonNull
    @Singleton
    AppComponent appComponent;

    @Nullable
    @PerServer
    protected ServerComponent serverComponent;

    @Nullable
    @PerUser
    protected UserComponent userComponent;

    @Nullable
    @PerActivity
    LoginComponent loginComponent;

    @Nullable
    @PerActivity
    private TeiDashboardComponent dashboardComponent;

    @Nullable
    private SessionComponent sessionComponent;

    private boolean fromBackGround = false;
    private boolean recreated;
    private AppInspector appInspector;

    @Override
    public void onCreate() {
        super.onCreate();

        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);

        appInspector = new AppInspector(this).init();

        MapController.Companion.init(this, BuildConfig.MAPBOX_ACCESS_TOKEN);

        setUpAppComponent();
        Timber.plant(BuildConfig.DEBUG ? new DebugTree() : new ReleaseTree(appComponent.injectCrashReportController()));

        setUpServerComponent();
        setUpRxPlugin();
        initCustomCrashActivity();
    }

    private void initCustomCrashActivity() {
        CaocConfig.Builder.create()
                .errorDrawable(R.drawable.ic_dhis)
                .apply();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void setUpAppComponent() {
        appComponent = prepareAppComponent().build();
        appComponent.inject(this);
    }

    protected void setUpServerComponent() {
        D2 d2Configuration = D2Manager.blockingInstantiateD2(ServerModule.getD2Configuration(this));
        boolean isLogged = d2Configuration.userModule().isLogged().blockingGet();
        serverComponent = appComponent.plus(new ServerModule());

        if (isLogged)
            setUpUserComponent();
    }


    protected void setUpUserComponent() {
        UserManager userManager = serverComponent == null
                ? null : serverComponent.userManager();
        if (userManager != null && userManager.isUserLoggedIn().blockingFirst()) {
            userComponent = serverComponent.plus(new UserModule());
        }
    }

    ////////////////////////////////////////////////////////////////////////
    // App component
    ////////////////////////////////////////////////////////////////////////
    @NonNull
    protected AppComponent.Builder prepareAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .schedulerModule(new SchedulerModule(new SchedulersProviderImpl()))
                .analyticsModule(new AnalyticsModule())
                .preferenceModule(new PreferenceModule())
                .workManagerController(new WorkManagerModule())
                .crashReportModule(new CrashReportModule());
    }

    @NonNull
    @Override
    public AppComponent appComponent() {
        return appComponent;
    }

    ////////////////////////////////////////////////////////////////////////
    // Login component
    ////////////////////////////////////////////////////////////////////////

    @NonNull
    @Override
    public LoginComponent createLoginComponent(LoginContracts.View view) {
        return (loginComponent = appComponent.plus(new LoginModule(view)));
    }

    @Nullable
    @Override
    public LoginComponent loginComponent() {
        return loginComponent;
    }

    @Override
    public void releaseLoginComponent() {
        loginComponent = null;
    }

    ////////////////////////////////////////////////////////////////////////
    // Server component
    ////////////////////////////////////////////////////////////////////////

    @Override
    public ServerComponent createServerComponent() {
        if (serverComponent == null)
            serverComponent = appComponent.plus(new ServerModule());
        return serverComponent;

    }

    @Nullable
    @Override
    public ServerComponent serverComponent() {
        return serverComponent;
    }

    @Override
    public void releaseServerComponent() {
        serverComponent = null;
    }

    public ServerComponent getServerComponent() {
        return serverComponent;
    }

    ////////////////////////////////////////////////////////////////////////
    // User component
    ////////////////////////////////////////////////////////////////////////

    @Override
    public UserComponent createUserComponent() {
        return (userComponent = serverComponent.plus(new UserModule()));
    }

    @Override
    public UserComponent userComponent() {
        return userComponent;
    }

    @Override
    public void releaseUserComponent() {
        userComponent = null;
    }

    ////////////////////////////////////////////////////////////////////////
    // Dashboard component
    ////////////////////////////////////////////////////////////////////////
    @NonNull
    public TeiDashboardComponent createDashboardComponent(@NonNull TeiDashboardModule dashboardModule) {
        if (dashboardComponent != null) {
            this.recreated = true;
        }
        dashboardComponent = userComponent.plus(dashboardModule);
        return dashboardComponent;
    }

    @Nullable
    public TeiDashboardComponent dashboardComponent() {
        return dashboardComponent;
    }

    public void releaseDashboardComponent() {
        if (!this.recreated) {
            dashboardComponent = null;
        } else {
            recreated = false;
        }
    }

    @NotNull
    public SessionComponent createSessionComponent(PinModule pinModule) {
        return (sessionComponent = appComponent.plus(pinModule));
    }

    public void releaseSessionComponent() {
        sessionComponent = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onAppBackgrounded() {
        Timber.tag("BG").d("App in background");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onAppForegrounded() {
        Timber.tag("BG").d("App in foreground");
        fromBackGround = true;
    }

    public void disableBackGroundFlag() {
        fromBackGround = false;
    }

    public boolean isSessionBlocked() {
        boolean shouldShowPinDialog = fromBackGround && appComponent().preferenceProvider().getBoolean(Preference.SESSION_LOCKED, false);
        fromBackGround = false;
        return shouldShowPinDialog;
    }

    private void setUpRxPlugin() {
        Scheduler asyncMainThreadScheduler = AndroidSchedulers.from(Looper.getMainLooper(), true);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> asyncMainThreadScheduler);
        RxJavaPlugins.setErrorHandler(e -> {
            if (e instanceof UndeliverableException) {
                e = e.getCause();
            }
            if ((e instanceof IOException) || (e instanceof SocketException)) {
                return;
            }
            if ((e instanceof NullPointerException) || (e instanceof IllegalArgumentException)) {
                Timber.d("Error in app");
                Thread.currentThread().getUncaughtExceptionHandler()
                        .uncaughtException(Thread.currentThread(), e);
            }
            if (e instanceof IllegalStateException) {
                Timber.d("Error in RxJava");
                Thread.currentThread().getUncaughtExceptionHandler()
                        .uncaughtException(Thread.currentThread(), e);
            }
            Timber.d(e);
        });
    }

    public AppInspector getAppInspector() {
        return appInspector;
    }
}
