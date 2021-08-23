package org.medhis2yes.usescases.datasets.datasetDetail;

import org.medhis2yes.data.dagger.PerActivity;

import dagger.Subcomponent;


@Subcomponent (modules = DataSetDetailModule.class)
@PerActivity
public interface DataSetDetailComponent {
    void inject(DataSetDetailActivity activity);
}
