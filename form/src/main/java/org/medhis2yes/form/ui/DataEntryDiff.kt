package org.medhis2yes.form.ui

import androidx.recyclerview.widget.DiffUtil
import org.medhis2yes.form.model.FieldUiModel

class DataEntryDiff : DiffUtil.ItemCallback<FieldUiModel>() {
    override fun areItemsTheSame(oldItem: FieldUiModel, newItem: FieldUiModel): Boolean =
        oldItem.uid == newItem.uid

    override fun areContentsTheSame(oldItem: FieldUiModel, newItem: FieldUiModel): Boolean =
        oldItem.equals(newItem)
}
