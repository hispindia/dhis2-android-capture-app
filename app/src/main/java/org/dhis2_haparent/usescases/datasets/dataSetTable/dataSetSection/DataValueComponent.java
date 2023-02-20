package org.dhis2_haparent.usescases.datasets.dataSetTable.dataSetSection;

import org.dhis2_haparent.commons.di.dagger.PerFragment;

import dagger.Subcomponent;

@PerFragment
@Subcomponent(modules = DataValueModule.class)
public interface DataValueComponent {
    void inject(DataSetSectionFragment fragment);
}
