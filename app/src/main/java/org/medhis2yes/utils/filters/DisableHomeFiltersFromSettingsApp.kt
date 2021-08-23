package org.medhis2yes.utils.filters

import org.medhis2yes.data.filter.StateFilter

class DisableHomeFiltersFromSettingsApp {

    fun execute(filters: List<FilterItem>) {
        val isOrgUnit = filters.filterIsInstance<OrgUnitFilter>().isNotEmpty()
        val isSync = filters.filterIsInstance<StateFilter>().isNotEmpty()
        val isPeriod = filters.filterIsInstance<PeriodFilter>().isNotEmpty()

        if (!isOrgUnit) FilterManager.getInstance().clearOuFilter()
        if (!isSync) FilterManager.getInstance().clearSyncFilter()
        if (!isPeriod) FilterManager.getInstance().clearPeriodFilter()
    }
}
