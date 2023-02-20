package org.dhis2_haparent.usescases.searchTrackEntity.adapters

import androidx.recyclerview.widget.RecyclerView
import org.dhis2_haparent.commons.data.SearchTeiModel
import org.dhis2_haparent.databinding.ItemSearchErrorBinding

class SearchErrorViewHolder(
    private val binding: ItemSearchErrorBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: SearchTeiModel) {
        binding.errorText.text = item.onlineErrorMessage
    }
}
