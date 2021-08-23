package org.medhis2yes.usescases.datasets.dataSetTable.dataSetSection

import android.view.View
import android.widget.TextView
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import medhis2yes.org.R

class DataSetIndicatorHolder(itemView: View) : AbstractViewHolder(itemView) {
    fun bind(text: String) {
        itemView.findViewById<TextView>(R.id.text).text = text
    }
}
