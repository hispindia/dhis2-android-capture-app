package org.dhis2hiv.usescases.datasets.dataSetTable;

import org.dhis2hiv.data.dagger.PerActivity;
import org.dhis2hiv.usescases.datasets.dataSetTable.dataSetDetail.DataSetDetailComponent;
import org.dhis2hiv.usescases.datasets.dataSetTable.dataSetDetail.DataSetDetailModule;

import dagger.Subcomponent;

@Subcomponent(modules = DataSetTableModule.class)
@PerActivity
public interface DataSetTableComponent {
    void inject(DataSetTableActivity activity);

    DataSetDetailComponent plus(DataSetDetailModule dataSetDetailModule);
}


