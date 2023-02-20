package org.dhis2_haparent.commons.orgunitselector

import org.hisp.dhis.android.core.organisationunit.OrganisationUnit

interface OnOrgUnitSelectionFinished {
    fun onSelectionFinished(selectedOrgUnits: List<OrganisationUnit>)
}
