package org.fpandhis2.usescases.datasets.dataSetTable;

import org.fpandhis2.data.dagger.PerActivity;
import org.fpandhis2.usescases.datasets.dataSetTable.dataSetDetail.DataSetDetailComponent;
import org.fpandhis2.usescases.datasets.dataSetTable.dataSetDetail.DataSetDetailModule;

import dagger.Subcomponent;

@Subcomponent(modules = DataSetTableModule.class)
@PerActivity
public interface DataSetTableComponent {
    void inject(DataSetTableActivity activity);

    DataSetDetailComponent plus(DataSetDetailModule dataSetDetailModule);
}


