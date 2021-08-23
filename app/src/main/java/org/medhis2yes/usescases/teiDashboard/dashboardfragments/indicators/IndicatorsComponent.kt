package org.medhis2yes.usescases.teiDashboard.dashboardfragments.indicators

import dagger.Subcomponent
import org.medhis2yes.data.dagger.PerFragment

@PerFragment
@Subcomponent(modules = [IndicatorsModule::class])
interface IndicatorsComponent {
    fun inject(indicatorsFragment: IndicatorsFragment)
}
