package org.medhis2yes.utils.granularsync

import androidx.recyclerview.widget.RecyclerView
import org.medhis2yes.databinding.ItemSyncConflictBinding
import org.medhis2yes.utils.DateUtils

class SyncConflictHolder(private val binding: ItemSyncConflictBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(trackerImportConflict: StatusLogItem) {
        binding.date.text = DateUtils.dateTimeFormat().format(trackerImportConflict.date())
        binding.message.text = trackerImportConflict.description()
    }
}
