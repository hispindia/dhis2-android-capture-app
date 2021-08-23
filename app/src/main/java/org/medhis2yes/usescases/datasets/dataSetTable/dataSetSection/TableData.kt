package org.medhis2yes.usescases.datasets.dataSetTable.dataSetSection

import org.medhis2yes.Bindings.maxLengthLabel
import org.medhis2yes.data.forms.dataentry.tablefields.FieldViewModel

data class TableData(
    val dataTableModel: DataTableModel,
    val fieldViewModels: List<List<FieldViewModel>>,
    val cells: List<List<String>>,
    val accessDataWrite: Boolean
) {
    fun columnHeaders() = dataTableModel.header()
    fun catCombo() = dataTableModel.catCombo()
    fun maxLengthLabel() = dataTableModel.rows()?.maxLengthLabel()
    fun maxColumns() = dataTableModel.header()!![dataTableModel.header()!!.size - 1].size
    fun rows() = dataTableModel.rows()
}
