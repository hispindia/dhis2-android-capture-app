package org.dhis2hiv.usescases.teiDashboard.dashboardfragments.indicators

import dagger.Subcomponent
import org.dhis2hiv.data.dagger.PerFragment

@PerFragment
@Subcomponent(modules = [IndicatorsModule::class])
interface IndicatorsComponent {
    fun inject(indicatorsFragment: IndicatorsFragment)
}
