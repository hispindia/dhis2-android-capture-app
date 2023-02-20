package dhis2.org.analytics.charts.ui

import androidx.recyclerview.widget.RecyclerView
import dhis2_haparent.org.databinding.ItemSectionTittleBinding

class SectionTitleViewHolder(
    val binding: ItemSectionTittleBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(sectionTitle: SectionTitle) {
        binding.sectionModel = sectionTitle
    }
}
