package org.medhis2yes.usescases.datasets.dataSetTable;

import org.medhis2yes.data.dagger.PerActivity;
import org.medhis2yes.usescases.datasets.dataSetTable.dataSetDetail.DataSetDetailComponent;
import org.medhis2yes.usescases.datasets.dataSetTable.dataSetDetail.DataSetDetailModule;

import dagger.Subcomponent;

@Subcomponent(modules = DataSetTableModule.class)
@PerActivity
public interface DataSetTableComponent {
    void inject(DataSetTableActivity activity);

    DataSetDetailComponent plus(DataSetDetailModule dataSetDetailModule);
}


