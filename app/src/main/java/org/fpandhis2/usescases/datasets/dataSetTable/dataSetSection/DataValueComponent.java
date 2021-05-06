package org.fpandhis2.usescases.datasets.dataSetTable.dataSetSection;

import org.fpandhis2.data.dagger.PerFragment;

import dagger.Subcomponent;

@PerFragment
@Subcomponent(modules = DataValueModule.class)
public interface DataValueComponent {
    void inject(DataSetSectionFragment fragment);
}
