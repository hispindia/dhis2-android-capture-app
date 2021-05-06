package org.dhis2afgamis.usescases.datasets.dataSetTable.dataSetSection;

import org.dhis2afgamis.data.dagger.PerFragment;

import dagger.Subcomponent;

@PerFragment
@Subcomponent(modules = DataValueModule.class)
public interface DataValueComponent {
    void inject(DataSetSectionFragment fragment);
}
