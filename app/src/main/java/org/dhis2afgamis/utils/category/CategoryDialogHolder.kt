package org.dhis2afgamis.utils.category

import androidx.recyclerview.widget.RecyclerView
import org.dhis2afgamis.databinding.ItemOptionBinding

class CategoryDialogHolder internal constructor(
    private val binding: ItemOptionBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CategoryDialogItem, listener: (CategoryDialogItem) -> Unit) {
        binding.option = item.displayName
        binding.executePendingBindings()

        itemView.setOnClickListener { listener(item) }
    }
}
