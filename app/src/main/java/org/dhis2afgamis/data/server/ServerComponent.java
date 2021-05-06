package org.dhis2afgamis.data.server;

import androidx.annotation.NonNull;

import org.dhis2afgamis.data.dagger.PerServer;
import org.dhis2afgamis.data.user.UserComponent;
import org.dhis2afgamis.data.user.UserModule;
import org.dhis2afgamis.utils.category.CategoryDialogComponent;
import org.dhis2afgamis.utils.category.CategoryDialogModule;
import org.dhis2afgamis.utils.customviews.CategoryComboDialogComponent;
import org.dhis2afgamis.utils.customviews.CategoryComboDialogModule;
import org.dhis2afgamis.utils.granularsync.GranularSyncComponent;
import org.dhis2afgamis.utils.granularsync.GranularSyncModule;

import dagger.Subcomponent;

@PerServer
@Subcomponent(modules = {ServerModule.class})
public interface ServerComponent {

    @NonNull
    UserManager userManager();

    @NonNull
    UserComponent plus(@NonNull UserModule userModule);

    @NonNull
    GranularSyncComponent plus(@NonNull GranularSyncModule granularSyncModule);

    @NonNull
    CategoryComboDialogComponent plus(@NonNull CategoryComboDialogModule categoryComboDialogModule);

    @NonNull
    CategoryDialogComponent plus(@NonNull CategoryDialogModule categoryDialogModule);

}
