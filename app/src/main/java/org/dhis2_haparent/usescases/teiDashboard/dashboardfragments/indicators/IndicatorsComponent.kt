package org.dhis2_haparent.usescases.teiDashboard.dashboardfragments.indicators

import dagger.Subcomponent
import org.dhis2_haparent.commons.di.dagger.PerFragment

@PerFragment
@Subcomponent(modules = [IndicatorsModule::class])
interface IndicatorsComponent {
    fun inject(indicatorsFragment: IndicatorsFragment)
}
