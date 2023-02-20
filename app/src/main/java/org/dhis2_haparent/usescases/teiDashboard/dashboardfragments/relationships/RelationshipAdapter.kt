package org.dhis2_haparent.usescases.teiDashboard.dashboardfragments.relationships

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import org.dhis2_haparent.R
import org.dhis2_haparent.commons.data.RelationshipViewModel
import org.dhis2_haparent.databinding.ItemRelationshipBinding

class RelationshipAdapter(private val presenter: RelationshipPresenter) :
    ListAdapter<RelationshipViewModel, RelationshipViewHolder>(object :
            DiffUtil.ItemCallback<RelationshipViewModel>() {
            override fun areItemsTheSame(
                oldItem: RelationshipViewModel,
                newItem: RelationshipViewModel
            ): Boolean {
                return oldItem.relationship.uid() == newItem.relationship.uid()
            }

            override fun areContentsTheSame(
                oldItem: RelationshipViewModel,
                newItem: RelationshipViewModel
            ): Boolean {
                return oldItem == newItem
            }
        }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelationshipViewHolder {
        val binding: ItemRelationshipBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_relationship,
            parent,
            false
        )
        return RelationshipViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RelationshipViewHolder, position: Int) {
        holder.bind(presenter, getItem(position))
    }
}
