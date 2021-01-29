package org.dhis2hiv.utils.filters.sorting

import org.hisp.dhis.android.core.organisationunit.OrganisationUnit

data class FilteredOrgUnitResult(private val orgUnits: List<OrganisationUnit>) {
    fun hasResult(): Boolean = orgUnits.isNotEmpty()
    fun names(): List<String> = orgUnits.map { it.displayName()!! }
    fun firstResult(): OrganisationUnit? = orgUnits.firstOrNull()
}
