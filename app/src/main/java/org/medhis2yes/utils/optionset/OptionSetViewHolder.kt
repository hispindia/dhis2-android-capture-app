package org.medhis2yes.utils.optionset

import androidx.recyclerview.widget.RecyclerView
import org.medhis2yes.databinding.ItemOptionBinding
import org.medhis2yes.utils.customviews.OptionSetOnClickListener
import org.hisp.dhis.android.core.option.Option

class OptionSetViewHolder internal constructor(
    private val binding: ItemOptionBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(option: Option, listener: OptionSetOnClickListener) {
        binding.option = option.displayName()
        binding.executePendingBindings()

        itemView.setOnClickListener { listener.onSelectOption(option) }
    }
}
