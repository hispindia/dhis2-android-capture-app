package org.medhis2yes.usescases.datasets.dataSetTable.dataSetSection;

import org.medhis2yes.data.dagger.PerFragment;

import dagger.Subcomponent;

@PerFragment
@Subcomponent(modules = DataValueModule.class)
public interface DataValueComponent {
    void inject(DataSetSectionFragment fragment);
}
