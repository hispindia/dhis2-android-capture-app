package org.medhis2yes.usescases.teiDashboard.dashboardfragments.indicators

import androidx.recyclerview.widget.RecyclerView
import org.medhis2yes.R
import org.medhis2yes.data.analytics.IndicatorModel
import org.medhis2yes.databinding.ItemIndicatorBinding
import org.medhis2yes.utils.Constants
import org.medhis2yes.utils.customviews.CustomDialog
import org.hisp.dhis.android.core.program.ProgramIndicator

class IndicatorViewHolder(
    val binding: ItemIndicatorBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(programIndicatorModel: IndicatorModel) {
        binding.indicatorModel = programIndicatorModel
        binding.descriptionLabel.setOnClickListener {
            showDescription(programIndicatorModel.programIndicator!!)
        }
    }

    private fun showDescription(programIndicatorModel: ProgramIndicator) {
        CustomDialog(
            itemView.context,
            programIndicatorModel.displayName()!!,
            programIndicatorModel.displayDescription()!!,
            itemView.getContext().getString(R.string.action_accept),
            null,
            Constants.DESCRIPTION_DIALOG,
            null
        ).show()
    }
}
