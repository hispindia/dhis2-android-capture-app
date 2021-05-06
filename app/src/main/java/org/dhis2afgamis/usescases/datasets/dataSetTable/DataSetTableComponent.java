package org.dhis2afgamis.usescases.datasets.dataSetTable;

import org.dhis2afgamis.data.dagger.PerActivity;
import org.dhis2afgamis.usescases.datasets.dataSetTable.dataSetDetail.DataSetDetailComponent;
import org.dhis2afgamis.usescases.datasets.dataSetTable.dataSetDetail.DataSetDetailModule;

import dagger.Subcomponent;

@Subcomponent(modules = DataSetTableModule.class)
@PerActivity
public interface DataSetTableComponent {
    void inject(DataSetTableActivity activity);

    DataSetDetailComponent plus(DataSetDetailModule dataSetDetailModule);
}


