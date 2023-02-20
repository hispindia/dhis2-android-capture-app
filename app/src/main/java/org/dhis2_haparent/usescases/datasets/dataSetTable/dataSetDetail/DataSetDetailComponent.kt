package org.dhis2_haparent.usescases.datasets.dataSetTable.dataSetDetail

import dagger.Subcomponent
import org.dhis2_haparent.commons.di.dagger.PerFragment

@Subcomponent(modules = [DataSetDetailModule::class])
@PerFragment
interface DataSetDetailComponent {
    fun inject(dataSetDetailFragment: DataSetDetailFragment)
}
