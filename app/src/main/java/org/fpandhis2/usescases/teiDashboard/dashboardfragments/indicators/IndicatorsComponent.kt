package org.fpandhis2.usescases.teiDashboard.dashboardfragments.indicators

import dagger.Subcomponent
import org.fpandhis2.data.dagger.PerFragment

@PerFragment
@Subcomponent(modules = [IndicatorsModule::class])
interface IndicatorsComponent {
    fun inject(indicatorsFragment: IndicatorsFragment)
}
