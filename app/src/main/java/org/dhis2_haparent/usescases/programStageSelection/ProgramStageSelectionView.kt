package org.dhis2_haparent.usescases.programStageSelection

import org.dhis2_haparent.usescases.general.AbstractActivityContracts
import org.hisp.dhis.android.core.period.PeriodType
import org.hisp.dhis.android.core.program.ProgramStage

interface ProgramStageSelectionView : AbstractActivityContracts.View {
    fun setData(programStages: List<ProgramStage>)
    fun setResult(programStageUid: String, repeatable: Boolean, periodType: PeriodType?)
}
