package org.medhis2yes.usescases.datasets.dataSetTable.dataSetDetail

import dagger.Subcomponent
import org.medhis2yes.data.dagger.PerFragment

@Subcomponent(modules = [DataSetDetailModule::class])
@PerFragment
interface DataSetDetailComponent {
    fun inject(dataSetDetailFragment: DataSetDetailFragment)
}
