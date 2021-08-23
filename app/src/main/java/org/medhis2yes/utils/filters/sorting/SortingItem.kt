package org.medhis2yes.utils.filters.sorting

import org.medhis2yes.utils.filters.Filters

data class SortingItem(
    var filterSelectedForSorting: Filters,
    var sortingStatus: SortingStatus = SortingStatus.ASC
) {
    companion object {
        @JvmStatic
        fun create(filter: Filters) = SortingItem(filter)
    }
}
