package org.dhis2_haparent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.dhis2_haparent.commons.components.ComponentProvider;
import org.dhis2_haparent.commons.dialogs.calendarpicker.di.CalendarPickerComponentProvider;
import org.dhis2_haparent.commons.featureconfig.di.FeatureConfigComponentProvider;
import org.dhis2_haparent.commons.filters.di.FilterPresenterProvider;
import org.dhis2_haparent.commons.orgunitselector.OUTreeComponentProvider;
import org.dhis2_haparent.data.server.ServerComponent;
import org.dhis2_haparent.data.user.UserComponent;
import org.dhis2_haparent.usescases.login.LoginComponent;
import org.dhis2_haparent.usescases.login.LoginContracts;

import dhis2.org.analytics.charts.di.AnalyticsComponentProvider;

public interface Components extends FeatureConfigComponentProvider,
        AnalyticsComponentProvider,
        CalendarPickerComponentProvider,
        FilterPresenterProvider,
        OUTreeComponentProvider,
        ComponentProvider {

    @NonNull
    AppComponent appComponent();

    ///////////////////////////////////////////////////////////////////
    // Login component
    ///////////////////////////////////////////////////////////////////


    @NonNull
    LoginComponent createLoginComponent(LoginContracts.View view);

    @Nullable
    LoginComponent loginComponent();

    void releaseLoginComponent();


    ////////////////////////////////////////////////////////////////////
    // Server component
    ///////////////////////////////////////////////////////////////////

    @NonNull
    ServerComponent createServerComponent();

    @Nullable
    ServerComponent serverComponent();

    void releaseServerComponent();

    ////////////////////////////////////////////////////////////////////
    // User component
    ////////////////////////////////////////////////////////////////////

    @NonNull
    UserComponent createUserComponent();

    @Nullable
    UserComponent userComponent();

    void releaseUserComponent();
}
