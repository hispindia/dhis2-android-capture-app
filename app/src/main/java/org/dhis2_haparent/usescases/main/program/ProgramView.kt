package org.dhis2_haparent.usescases.main.program

import org.dhis2_haparent.usescases.general.AbstractActivityContracts

interface ProgramView : AbstractActivityContracts.View {

    fun swapProgramModelData(programs: List<ProgramViewModel>)

    fun showFilterProgress()

    fun openOrgUnitTreeSelector()

    fun showHideFilter()

    fun clearFilters()

    fun navigateTo(program: ProgramViewModel)

    fun showSyncDialog(program: ProgramViewModel)
}
