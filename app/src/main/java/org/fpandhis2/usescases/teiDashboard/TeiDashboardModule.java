package org.fpandhis2.usescases.teiDashboard;

import androidx.annotation.NonNull;

import org.fpandhis2.data.dagger.PerActivity;
import org.fpandhis2.data.forms.EnrollmentFormRepository;
import org.fpandhis2.data.forms.FormRepository;
import org.fpandhis2.data.forms.RulesRepository;
import org.fpandhis2.data.prefs.PreferenceProvider;
import org.fpandhis2.data.schedulers.SchedulerProvider;
import org.fpandhis2.utils.analytics.AnalyticsHelper;
import org.fpandhis2.utils.filters.FilterManager;
import org.fpandhis2.utils.resources.ResourceManager;
import org.hisp.dhis.android.core.D2;
import org.hisp.dhis.rules.RuleExpressionEvaluator;

import dagger.Module;
import dagger.Provides;

/**
 * QUADRAM. Created by ppajuelo on 30/11/2017.
 */
@PerActivity
@Module
public class TeiDashboardModule {

    public final String programUid;
    public final String teiUid;
    private final TeiDashboardContracts.View view;
    private final String enrollmentUid;

    public TeiDashboardModule(TeiDashboardContracts.View view, String teiUid, String programUid, String enrollmentUid) {
        this.view = view;
        this.teiUid = teiUid;
        this.programUid = programUid;
        this.enrollmentUid = enrollmentUid;
    }

    @Provides
    @PerActivity
    TeiDashboardContracts.View provideView(TeiDashboardMobileActivity mobileActivity) {
        return mobileActivity;
    }

    @Provides
    @PerActivity
    TeiDashboardContracts.Presenter providePresenter(DashboardRepository dashboardRepository,
                                                     SchedulerProvider schedulerProvider,
                                                     AnalyticsHelper analyticsHelper,
                                                     PreferenceProvider preferenceProvider,
                                                     FilterManager filterManager) {
        return new TeiDashboardPresenter(view,
                teiUid,
                programUid,
                enrollmentUid,
                dashboardRepository,
                schedulerProvider,
                analyticsHelper,
                preferenceProvider,
                filterManager);
    }

    @Provides
    @PerActivity
    DashboardRepository dashboardRepository(D2 d2, ResourceManager resources) {
        return new DashboardRepositoryImpl(d2, teiUid, programUid, enrollmentUid, resources);
    }

    @Provides
    @PerActivity
    RulesRepository rulesRepository(@NonNull D2 d2) {
        return new RulesRepository(d2);
    }

    @Provides
    @PerActivity
    FormRepository formRepository(
            @NonNull RuleExpressionEvaluator evaluator,
            @NonNull RulesRepository rulesRepository,
            D2 d2) {
        String enrollmentUidToUse = enrollmentUid != null ? enrollmentUid : "";
        return new EnrollmentFormRepository(evaluator, rulesRepository, enrollmentUidToUse, d2);
    }
}
