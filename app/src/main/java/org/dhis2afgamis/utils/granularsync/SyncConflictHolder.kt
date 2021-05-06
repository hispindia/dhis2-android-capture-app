package org.dhis2afgamis.utils.granularsync

import androidx.recyclerview.widget.RecyclerView
import org.dhis2afgamis.databinding.ItemSyncConflictBinding
import org.dhis2afgamis.utils.DateUtils

class SyncConflictHolder(private val binding: ItemSyncConflictBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(trackerImportConflict: StatusLogItem) {
        binding.date.text = DateUtils.dateTimeFormat().format(trackerImportConflict.date())
        binding.message.text = trackerImportConflict.description()
    }
}
