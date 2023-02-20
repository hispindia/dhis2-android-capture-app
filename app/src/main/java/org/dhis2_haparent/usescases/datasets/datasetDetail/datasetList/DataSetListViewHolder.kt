package org.dhis2_haparent.usescases.datasets.datasetDetail.datasetList

import androidx.recyclerview.widget.RecyclerView
import org.dhis2_haparent.databinding.ItemDatasetBinding
import org.dhis2_haparent.usescases.datasets.datasetDetail.DataSetDetailModel

class DataSetListViewHolder(private val binding: ItemDatasetBinding) : RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(dataSet: DataSetDetailModel, viewModel: DataSetListViewModel) {
        binding.viewModel = viewModel
        binding.dataset = dataSet
        binding.executePendingBindings()
    }
}
