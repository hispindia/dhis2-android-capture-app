package org.dhis2hiv.usescases.main.program

import androidx.annotation.UiThread
import org.dhis2hiv.usescases.general.AbstractActivityContracts

interface ProgramView : AbstractActivityContracts.View {

    fun swapProgramModelData(programs: List<ProgramViewModel>)

    fun showFilterProgress()

    @UiThread
    fun renderError(message: String)

    fun openOrgUnitTreeSelector()

    fun showHideFilter()

    fun clearFilters()

    fun navigateTo(program: ProgramViewModel)

    fun showSyncDialog(program: ProgramViewModel)
}
