package org.medhis2yes.usescases.teiDashboard.dashboardfragments.indicators

import androidx.recyclerview.widget.RecyclerView
import org.medhis2yes.data.analytics.SectionTitle
import org.medhis2yes.databinding.ItemSectionTittleBinding

class SectionTitleViewHolder(
    val binding: ItemSectionTittleBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(sectionTitle: SectionTitle) {
        binding.sectionModel = sectionTitle
    }
}
