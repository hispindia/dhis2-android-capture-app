package org.fpandhis2.data.forms.dataentry

import androidx.recyclerview.widget.DiffUtil
import org.fpandhis2.data.forms.dataentry.fields.FieldViewModel
import org.fpandhis2.data.forms.dataentry.fields.optionset.OptionSetViewModel
import org.fpandhis2.data.forms.dataentry.fields.section.SectionViewModel
import org.fpandhis2.data.forms.dataentry.fields.spinner.SpinnerViewModel

class DataEntryDiff : DiffUtil.ItemCallback<FieldViewModel>() {
    override fun areItemsTheSame(oldItem: FieldViewModel, newItem: FieldViewModel): Boolean =
        oldItem.uid() == newItem.uid()

    override fun areContentsTheSame(oldItem: FieldViewModel, newItem: FieldViewModel): Boolean =
        if (newItem is SectionViewModel ||
            oldItem is SectionViewModel ||
            oldItem is OptionSetViewModel ||
            oldItem is SpinnerViewModel
        ) {
            false
        } else {
            oldItem == newItem
        }
}
