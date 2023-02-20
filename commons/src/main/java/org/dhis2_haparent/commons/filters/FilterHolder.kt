package org.dhis2_haparent.commons.filters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import org.dhis2_haparent.commons.BR

class FilterHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(filterItem: FilterItem) {
        binding.apply {
            setVariable(BR.filterItem, filterItem)
            setVariable(
                BR.workingListScope,
                FilterManager.getInstance().observeWorkingListScope()
            )
            executePendingBindings()
        }
    }
}
