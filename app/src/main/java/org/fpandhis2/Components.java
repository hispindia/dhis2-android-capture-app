package org.fpandhis2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.fpandhis2.data.server.ServerComponent;
import org.fpandhis2.data.user.UserComponent;

import org.fpandhis2.usescases.login.LoginComponent;
import org.fpandhis2.usescases.login.LoginContracts;

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
