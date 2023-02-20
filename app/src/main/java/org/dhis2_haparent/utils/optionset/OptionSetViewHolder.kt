package org.dhis2_haparent.utils.optionset

import androidx.recyclerview.widget.RecyclerView
import org.dhis2_haparent.databinding.ItemOptionBinding
import org.dhis2_haparent.utils.customviews.OptionSetOnClickListener
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
