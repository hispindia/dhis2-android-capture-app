package org.fpandhis2.utils.category

import androidx.recyclerview.widget.RecyclerView
import org.fpandhis2.databinding.ItemOptionBinding

class CategoryDialogHolder internal constructor(
    private val binding: ItemOptionBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CategoryDialogItem, listener: (CategoryDialogItem) -> Unit) {
        binding.option = item.displayName
        binding.executePendingBindings()

        itemView.setOnClickListener { listener(item) }
    }
}
