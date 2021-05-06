package org.dhis2afgamis.usescases.orgunitselector

import org.dhis2afgamis.usescases.general.AbstractActivityContracts

interface OUTreeView : AbstractActivityContracts.View {

    fun setOrgUnits(organisationUnits: List<TreeNode>)
    fun addOrgUnits(location: Int, organisationUnits: List<TreeNode>)
}
