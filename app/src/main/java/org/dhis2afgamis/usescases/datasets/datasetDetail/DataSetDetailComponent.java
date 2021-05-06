package org.dhis2afgamis.usescases.datasets.datasetDetail;

import org.dhis2afgamis.data.dagger.PerActivity;

import dagger.Subcomponent;


@Subcomponent (modules = DataSetDetailModule.class)
@PerActivity
public interface DataSetDetailComponent {
    void inject(DataSetDetailActivity activity);
}
