package org.dhis2_haparent.data.server;

import androidx.annotation.NonNull;

import org.dhis2_haparent.commons.di.dagger.PerServer;
import org.dhis2_haparent.data.dhislogic.DhisPeriodUtils;
import org.dhis2_haparent.data.user.UserComponent;
import org.dhis2_haparent.data.user.UserModule;
import org.dhis2_haparent.commons.orgunitselector.OUTreeComponent;
import org.dhis2_haparent.commons.orgunitselector.OUTreeModule;
import org.dhis2_haparent.ui.ThemeManager;
import org.dhis2_haparent.usescases.login.accounts.AccountsComponent;
import org.dhis2_haparent.usescases.login.accounts.AccountsModule;
import org.dhis2_haparent.utils.category.CategoryDialogComponent;
import org.dhis2_haparent.utils.category.CategoryDialogModule;
import org.dhis2_haparent.utils.customviews.CategoryComboDialogComponent;
import org.dhis2_haparent.utils.customviews.CategoryComboDialogModule;
import org.dhis2_haparent.utils.granularsync.GranularSyncComponent;
import org.dhis2_haparent.utils.granularsync.GranularSyncModule;

import dagger.Subcomponent;
import dhis2.org.analytics.charts.Charts;

@PerServer
@Subcomponent(modules = {ServerModule.class})
public interface ServerComponent extends Charts.Dependencies {

    @NonNull
    UserManager userManager();

    @NonNull
    OpenIdSession openIdSession();

    ServerStatus serverStatus();

    ThemeManager themeManager();

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

    AccountsComponent plus(@NonNull AccountsModule module);
}
