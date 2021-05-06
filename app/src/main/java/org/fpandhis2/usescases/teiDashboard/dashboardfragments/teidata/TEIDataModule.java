package org.fpandhis2.usescases.teiDashboard.dashboardfragments.teidata;

import androidx.annotation.NonNull;

import org.fpandhis2.data.dagger.PerFragment;
import org.fpandhis2.data.dhislogic.DhisEventUtils;
import org.fpandhis2.data.filter.FilterPresenter;
import org.fpandhis2.data.forms.FormRepository;
import org.fpandhis2.data.forms.dataentry.EnrollmentRuleEngineRepository;
import org.fpandhis2.data.forms.dataentry.RuleEngineRepository;
import org.fpandhis2.data.prefs.PreferenceProvider;
import org.fpandhis2.data.schedulers.SchedulerProvider;
import org.fpandhis2.usescases.teiDashboard.DashboardRepository;
import org.fpandhis2.utils.analytics.AnalyticsHelper;
import org.fpandhis2.utils.filters.FilterManager;
import org.fpandhis2.utils.filters.FiltersAdapter;
import org.hisp.dhis.android.core.D2;

import dagger.Module;
import dagger.Provides;

/**
 * QUADRAM. Created by ppajuelo on 09/04/2019.
 */
@PerFragment
@Module
public class TEIDataModule {

    private TEIDataContracts.View view;
    private final String programUid;
    private final String teiUid;
    private final String enrollmentUid;

    public TEIDataModule(TEIDataContracts.View view, String programUid, String teiUid, String enrollmentUid) {
        this.view = view;
        this.programUid = programUid;
        this.teiUid = teiUid;
        this.enrollmentUid = enrollmentUid;
    }

    @Provides
    @PerFragment
    TEIDataContracts.Presenter providesPresenter(D2 d2,
                                                 DashboardRepository dashboardRepository,
                                                 TeiDataRepository teiDataRepository,
                                                 RuleEngineRepository ruleEngineRepository,
                                                 SchedulerProvider schedulerProvider,
                                                 AnalyticsHelper analyticsHelper,
                                                 PreferenceProvider preferenceProvider,
                                                 FilterManager filterManager) {
        return new TEIDataPresenterImpl(view,
                d2,
                dashboardRepository,
                teiDataRepository,
                ruleEngineRepository,
                programUid,
                teiUid,
                enrollmentUid,
                schedulerProvider,
                preferenceProvider,
                analyticsHelper,
                filterManager);

    }

    @Provides
    @PerFragment
    TeiDataRepository providesRepository(D2 d2, DhisEventUtils dhisEventUtils) {
        return new TeiDataRepositoryImpl(d2,
                programUid,
                teiUid,
                enrollmentUid,
                dhisEventUtils);
    }

    @Provides
    @PerFragment
    RuleEngineRepository ruleEngineRepository(@NonNull FormRepository formRepository, D2 d2) {
        return new EnrollmentRuleEngineRepository(formRepository, enrollmentUid, d2);
    }

    @Provides
    @PerFragment
    FiltersAdapter provideFiltersAdapter(FilterPresenter filterPresenter) {
        return new FiltersAdapter(FiltersAdapter.ProgramType.DASHBOARD, filterPresenter);
    }
}
