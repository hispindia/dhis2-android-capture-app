package org.fpandhis2.usescases.datasets.dataSetTable;

import org.fpandhis2.data.dagger.PerActivity;
import org.fpandhis2.data.schedulers.SchedulerProvider;
import org.fpandhis2.usescases.datasets.datasetInitial.DataSetInitialRepository;
import org.fpandhis2.usescases.datasets.datasetInitial.DataSetInitialRepositoryImpl;
import org.fpandhis2.utils.analytics.AnalyticsHelper;
import org.hisp.dhis.android.core.D2;

import dagger.Module;
import dagger.Provides;

@PerActivity
@Module
public class DataSetTableModule {

    private DataSetTableContract.View view;
    private final String dataSetUid;
    private final String periodId;
    private final String orgUnitUid;
    private final String catOptCombo;

    DataSetTableModule(DataSetTableActivity view, String dataSetUid, String periodId, String orgUnitUid, String catOptCombo) {
        this.view = view;
        this.dataSetUid = dataSetUid;
        this.periodId = periodId;
        this.orgUnitUid = orgUnitUid;
        this.catOptCombo = catOptCombo;
    }

    @Provides
    @PerActivity
    DataSetTableContract.Presenter providesPresenter(
            DataSetTableRepositoryImpl dataSetTableRepository,
            SchedulerProvider schedulerProvider,
            AnalyticsHelper analyticsHelper) {
        return new DataSetTablePresenter(view, dataSetTableRepository, schedulerProvider, analyticsHelper);
    }

    @Provides
    @PerActivity
    DataSetTableRepositoryImpl DataSetTableRepository(D2 d2) {
        return new DataSetTableRepositoryImpl(d2, dataSetUid, periodId, orgUnitUid, catOptCombo);
    }

    @Provides
    @PerActivity
    DataSetInitialRepository DataSetInitialRepository(D2 d2) {
        return new DataSetInitialRepositoryImpl(d2, dataSetUid);
    }

}
