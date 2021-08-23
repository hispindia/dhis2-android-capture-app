package org.medhis2yes.utils.filters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import org.medhis2yes.BR

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
