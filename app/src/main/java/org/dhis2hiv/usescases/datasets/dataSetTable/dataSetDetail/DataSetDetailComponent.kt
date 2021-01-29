package org.dhis2hiv.usescases.datasets.dataSetTable.dataSetDetail

import dagger.Subcomponent
import org.dhis2hiv.data.dagger.PerFragment

@Subcomponent(modules = [DataSetDetailModule::class])
@PerFragment
interface DataSetDetailComponent {
    fun inject(dataSetDetailFragment: DataSetDetailFragment)
}
