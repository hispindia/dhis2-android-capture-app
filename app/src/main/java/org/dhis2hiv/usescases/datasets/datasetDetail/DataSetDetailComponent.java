package org.dhis2hiv.usescases.datasets.datasetDetail;

import org.dhis2hiv.data.dagger.PerActivity;

import dagger.Subcomponent;


@Subcomponent (modules = DataSetDetailModule.class)
@PerActivity
public interface DataSetDetailComponent {
    void inject(DataSetDetailActivity activity);
}
