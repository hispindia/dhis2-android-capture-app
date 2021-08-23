package org.medhis2yes.data.server;

import androidx.annotation.NonNull;

import org.medhis2yes.data.dagger.PerServer;
import org.medhis2yes.data.dhislogic.DhisPeriodUtils;
import org.medhis2yes.data.user.UserComponent;
import org.medhis2yes.data.user.UserModule;
import org.medhis2yes.usescases.orgunitselector.OUTreeComponent;
import org.medhis2yes.usescases.orgunitselector.OUTreeModule;
import org.medhis2yes.utils.category.CategoryDialogComponent;
import org.medhis2yes.utils.category.CategoryDialogModule;
import org.medhis2yes.utils.customviews.CategoryComboDialogComponent;
import org.medhis2yes.utils.customviews.CategoryComboDialogModule;
import org.medhis2yes.utils.granularsync.GranularSyncComponent;
import org.medhis2yes.utils.granularsync.GranularSyncModule;

import dagger.Subcomponent;
import dhis2.org.analytics.charts.Charts;

@PerServer
@Subcomponent(modules = {ServerModule.class})
public interface ServerComponent extends Charts.Dependencies {

    @NonNull
    UserManager userManager();

    @NonNull
    OpenIdSession openIdSession();

    @NonNull
    UserComponent plus(@NonNull UserModule userModule);

    @NonNull
    GranularSyncComponent plus(@NonNull GranularSyncModule granularSyncModule);

    @NonNull
    CategoryComboDialogComponent plus(@NonNull CategoryComboDialogModule categoryComboDialogModule);

    @NonNull
    CategoryDialogComponent plus(@NonNull CategoryDialogModule categoryDialogModule);

    @NonNull
    OUTreeComponent plus(@NonNull OUTreeModule ouTreeModule);

    @NonNull
    DhisPeriodUtils dhisPeriodUtils();
}
