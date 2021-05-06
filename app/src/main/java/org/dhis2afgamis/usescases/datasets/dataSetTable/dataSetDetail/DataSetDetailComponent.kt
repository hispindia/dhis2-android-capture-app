package org.dhis2afgamis.usescases.datasets.dataSetTable.dataSetDetail

import dagger.Subcomponent
import org.dhis2afgamis.data.dagger.PerFragment

@Subcomponent(modules = [DataSetDetailModule::class])
@PerFragment
interface DataSetDetailComponent {
    fun inject(dataSetDetailFragment: DataSetDetailFragment)
}
