package org.dhis2hiv.usescases.orgunitselector

import org.dhis2hiv.usescases.general.AbstractActivityContracts

interface OUTreeView : AbstractActivityContracts.View {

    fun setOrgUnits(organisationUnits: List<TreeNode>)
    fun addOrgUnits(location: Int, organisationUnits: List<TreeNode>)
}
