package org.dhis2hiv.usescases.teiDashboard.dashboardfragments.indicators

import org.dhis2hiv.data.tuples.Trio
import org.dhis2hiv.usescases.general.AbstractActivityContracts
import org.hisp.dhis.android.core.program.ProgramIndicator

interface IndicatorsView : AbstractActivityContracts.View {

    fun swapIndicators(indicators: List<Trio<ProgramIndicator, String, String>>)
}
