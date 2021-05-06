package org.fpandhis2.usescases.datasets.dataSetTable.dataSetDetail

import dagger.Subcomponent
import org.fpandhis2.data.dagger.PerFragment

@Subcomponent(modules = [DataSetDetailModule::class])
@PerFragment
interface DataSetDetailComponent {
    fun inject(dataSetDetailFragment: DataSetDetailFragment)
}
