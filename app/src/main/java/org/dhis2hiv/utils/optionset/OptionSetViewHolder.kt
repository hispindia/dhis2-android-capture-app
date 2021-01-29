package org.dhis2hiv.utils.optionset

import androidx.recyclerview.widget.RecyclerView
import org.dhis2hiv.databinding.ItemOptionBinding
import org.dhis2hiv.utils.customviews.OptionSetOnClickListener
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
