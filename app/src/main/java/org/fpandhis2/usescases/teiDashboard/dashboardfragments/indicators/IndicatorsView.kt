package org.fpandhis2.usescases.teiDashboard.dashboardfragments.indicators

import org.fpandhis2.data.tuples.Trio
import org.fpandhis2.usescases.general.AbstractActivityContracts
import org.hisp.dhis.android.core.program.ProgramIndicator

interface IndicatorsView : AbstractActivityContracts.View {

    fun swapIndicators(indicators: List<Trio<ProgramIndicator, String, String>>)
}
