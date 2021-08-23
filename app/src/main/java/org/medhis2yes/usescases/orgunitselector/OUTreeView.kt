package org.medhis2yes.usescases.orgunitselector

interface OUTreeView {
    fun setOrgUnits(organisationUnits: List<TreeNode>)
    fun addOrgUnits(location: Int, organisationUnits: List<TreeNode>)
}
