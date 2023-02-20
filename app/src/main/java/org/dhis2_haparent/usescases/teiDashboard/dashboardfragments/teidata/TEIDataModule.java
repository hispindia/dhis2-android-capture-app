package org.dhis2_haparent.usescases.teiDashboard.dashboardfragments.teidata;

import org.dhis2_haparent.commons.data.EntryMode;
import org.dhis2_haparent.commons.di.dagger.PerFragment;
import org.dhis2_haparent.commons.filters.FilterManager;
import org.dhis2_haparent.commons.filters.FiltersAdapter;
import org.dhis2_haparent.commons.filters.data.FilterRepository;
import org.dhis2_haparent.commons.network.NetworkUtils;
import org.dhis2_haparent.commons.prefs.PreferenceProvider;
import org.dhis2_haparent.commons.reporting.CrashReportController;
import org.dhis2_haparent.commons.resources.ResourceManager;
import org.dhis2_haparent.commons.schedulers.SchedulerProvider;
import org.dhis2_haparent.data.dhislogic.DhisEnrollmentUtils;
import org.dhis2_haparent.data.dhislogic.DhisPeriodUtils;
import org.dhis2_haparent.data.forms.dataentry.RuleEngineRepository;
import org.dhis2_haparent.data.forms.dataentry.SearchTEIRepository;
import org.dhis2_haparent.data.forms.dataentry.SearchTEIRepositoryImpl;
import org.dhis2_haparent.form.data.FormValueStore;
import org.dhis2_haparent.usescases.teiDashboard.DashboardRepository;
import org.dhis2_haparent.utils.analytics.AnalyticsHelper;
import org.dhis2_haparent.commons.reporting.CrashReportControllerImpl;
import org.hisp.dhis.android.core.D2;

import dagger.Module;
import dagger.Provides;

/**
 * QUADRAM. Created by ppajuelo on 09/04/2019.
 */
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
                                                 FilterRepository filterRepository,
                                                 FormValueStore valueStore) {
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
                filterRepository,
                valueStore);

    }

    @Provides
    @PerFragment
    SearchTEIRepository searchTEIRepository(D2 d2) {
        return new SearchTEIRepositoryImpl(d2, new DhisEnrollmentUtils(d2), new CrashReportControllerImpl());
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

    @Provides
    @PerFragment
    FormValueStore valueStore(
            D2 d2,
            CrashReportController crashReportController,
            NetworkUtils networkUtils,
            ResourceManager resourceManager
    ) {
        return new FormValueStore(
                d2,
                teiUid,
                EntryMode.ATTR,
                null,
                crashReportController,
                networkUtils,
                resourceManager
        );
    }
}
