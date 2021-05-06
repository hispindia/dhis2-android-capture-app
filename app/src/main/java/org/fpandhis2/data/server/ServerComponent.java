package org.fpandhis2.data.server;

import androidx.annotation.NonNull;

import org.fpandhis2.data.dagger.PerServer;
import org.fpandhis2.data.user.UserComponent;
import org.fpandhis2.data.user.UserModule;
import org.fpandhis2.utils.category.CategoryDialogComponent;
import org.fpandhis2.utils.category.CategoryDialogModule;
import org.fpandhis2.utils.customviews.CategoryComboDialogComponent;
import org.fpandhis2.utils.customviews.CategoryComboDialogModule;
import org.fpandhis2.utils.granularsync.GranularSyncComponent;
import org.fpandhis2.utils.granularsync.GranularSyncModule;

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
