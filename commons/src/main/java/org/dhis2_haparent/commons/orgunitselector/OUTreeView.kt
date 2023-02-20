package org.dhis2_haparent.commons.orgunitselector

interface OUTreeView {
    fun setOrgUnits(organisationUnits: List<TreeNode>)
    fun getCurrentList(): List<TreeNode>
}
