package org.dhis2hiv;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.dhis2hiv.data.server.ServerComponent;
import org.dhis2hiv.data.user.UserComponent;

import org.dhis2hiv.usescases.login.LoginComponent;
import org.dhis2hiv.usescases.login.LoginContracts;

public interface Components {

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
