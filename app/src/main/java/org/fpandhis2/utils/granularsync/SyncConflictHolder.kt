package org.fpandhis2.utils.granularsync

import androidx.recyclerview.widget.RecyclerView
import org.fpandhis2.databinding.ItemSyncConflictBinding
import org.fpandhis2.utils.DateUtils

class SyncConflictHolder(private val binding: ItemSyncConflictBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(trackerImportConflict: StatusLogItem) {
        binding.date.text = DateUtils.dateTimeFormat().format(trackerImportConflict.date())
        binding.message.text = trackerImportConflict.description()
    }
}
