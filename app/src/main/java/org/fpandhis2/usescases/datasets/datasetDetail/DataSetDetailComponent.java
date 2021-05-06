package org.fpandhis2.usescases.datasets.datasetDetail;

import org.fpandhis2.data.dagger.PerActivity;

import dagger.Subcomponent;


@Subcomponent (modules = DataSetDetailModule.class)
@PerActivity
public interface DataSetDetailComponent {
    void inject(DataSetDetailActivity activity);
}
