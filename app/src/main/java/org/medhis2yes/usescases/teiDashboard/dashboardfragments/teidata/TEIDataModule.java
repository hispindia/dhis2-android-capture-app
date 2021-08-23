package org.medhis2yes.usescases.teiDashboard.dashboardfragments.teidata;

import org.medhis2yes.data.dagger.PerFragment;
import org.medhis2yes.data.dhislogic.DhisPeriodUtils;
import org.medhis2yes.data.filter.FilterRepository;
import org.medhis2yes.data.forms.dataentry.RuleEngineRepository;
import org.medhis2yes.data.prefs.PreferenceProvider;
import org.medhis2yes.data.schedulers.SchedulerProvider;
import org.medhis2yes.usescases.teiDashboard.DashboardRepository;
import org.medhis2yes.utils.analytics.AnalyticsHelper;
import org.medhis2yes.utils.filters.FilterManager;
import org.medhis2yes.utils.filters.FiltersAdapter;
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
                                                 FilterManager filterManager,
                                                 FilterRepository filterRepository) {
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
                filterManager,
                filterRepository);

    }

    @Provides
    @PerFragment
    TeiDataRepository providesRepository(D2 d2, DhisPeriodUtils periodUtils) {
        return new TeiDataRepositoryImpl(d2,
                programUid,
                teiUid,
                enrollmentUid,
                periodUtils);
    }

    @Provides
    @PerFragment
    FiltersAdapter provideNewFiltersAdapter() {
        return new FiltersAdapter();
    }
}
