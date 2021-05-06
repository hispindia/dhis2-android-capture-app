package org.dhis2afgamis.usescases.teiDashboard.dashboardfragments.indicators

import org.dhis2afgamis.data.tuples.Trio
import org.dhis2afgamis.usescases.general.AbstractActivityContracts
import org.hisp.dhis.android.core.program.ProgramIndicator

interface IndicatorsView : AbstractActivityContracts.View {

    fun swapIndicators(indicators: List<Trio<ProgramIndicator, String, String>>)
}
