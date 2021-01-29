package org.dhis2hiv.usescases.datasets.dataSetTable.dataSetSection;

import org.dhis2hiv.data.dagger.PerFragment;

import dagger.Subcomponent;

@PerFragment
@Subcomponent(modules = DataValueModule.class)
public interface DataValueComponent {
    void inject(DataSetSectionFragment fragment);
}
