package org.dhis2afgamis.usescases.searchTrackEntity.adapters

import android.view.View
import org.dhis2afgamis.databinding.ItemSearchTrackedEntityBinding

class SearchRelationshipViewHolder(
    val binding: ItemSearchTrackedEntityBinding
) : BaseTeiViewHolder(binding) {

    override fun itemConfiguration() {
        binding.sortingFieldName.visibility = View.GONE
        binding.sortingFieldValue.visibility = View.GONE
        binding.syncState.visibility = View.GONE
    }
    override fun itemViewClick() {
        itemView.setOnClickListener {
            presenter.addRelationship(teiModel.tei.uid(), null, teiModel.isOnline)
        }
    }
}
