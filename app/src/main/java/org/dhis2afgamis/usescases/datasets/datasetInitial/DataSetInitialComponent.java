package org.dhis2afgamis.usescases.datasets.datasetInitial;

import org.dhis2afgamis.data.dagger.PerActivity;

import dagger.Subcomponent;

/**
 * QUADRAM. Created by ppajuelo on 24/09/2018.
 */

@Subcomponent(modules = DataSetInitialModule.class)
@PerActivity
public interface DataSetInitialComponent {
    void inject(DataSetInitialActivity activity);
}
