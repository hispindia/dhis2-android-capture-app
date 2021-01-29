package org.dhis2hiv.utils.granularsync

import androidx.recyclerview.widget.RecyclerView
import org.dhis2hiv.databinding.ItemSyncConflictBinding
import org.dhis2hiv.utils.DateUtils

class SyncConflictHolder(private val binding: ItemSyncConflictBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(trackerImportConflict: StatusLogItem) {
        binding.date.text = DateUtils.dateTimeFormat().format(trackerImportConflict.date())
        binding.message.text = trackerImportConflict.description()
    }
}
