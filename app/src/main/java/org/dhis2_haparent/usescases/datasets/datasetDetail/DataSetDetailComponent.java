package org.dhis2_haparent.usescases.datasets.datasetDetail;

import org.dhis2_haparent.commons.di.dagger.PerActivity;
import org.dhis2_haparent.usescases.datasets.datasetDetail.datasetList.DataSetListComponent;
import org.dhis2_haparent.usescases.datasets.datasetDetail.datasetList.DataSetListModule;

import dagger.Subcomponent;


@Subcomponent (modules = DataSetDetailModule.class)
@PerActivity
public interface DataSetDetailComponent {
    void inject(DataSetDetailActivity activity);
    DataSetListComponent plus(DataSetListModule dataSetListModule);
}
