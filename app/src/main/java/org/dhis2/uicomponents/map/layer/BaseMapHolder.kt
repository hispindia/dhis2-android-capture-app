package org.dhis2.uicomponents.map.layer

import androidx.recyclerview.widget.RecyclerView
import org.dhis2.databinding.BasemapItemBinding

class BaseMapHolder(
    val binding: BasemapItemBinding,
    private val onItemSelected: (BaseMapType) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(baseMap: BaseMap) {
        binding.apply {
            itemStyle = baseMap.basemapType
            baseMapImage.setImageResource(baseMap.basemapImage)
            basemapName.setText(baseMap.basemapName)
        }
        itemView.setOnClickListener { onItemSelected.invoke(baseMap.basemapType) }
    }
}
