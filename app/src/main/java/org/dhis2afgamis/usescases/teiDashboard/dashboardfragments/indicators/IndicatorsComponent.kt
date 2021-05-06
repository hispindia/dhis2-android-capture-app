package org.dhis2afgamis.usescases.teiDashboard.dashboardfragments.indicators

import dagger.Subcomponent
import org.dhis2afgamis.data.dagger.PerFragment

@PerFragment
@Subcomponent(modules = [IndicatorsModule::class])
interface IndicatorsComponent {
    fun inject(indicatorsFragment: IndicatorsFragment)
}
