package org.dhis2_haparent.usescases.datasets.dataSetTable.dataSetSection

import org.dhis2_haparent.Bindings.maxLengthLabel
import org.dhis2_haparent.data.forms.dataentry.tablefields.FieldViewModel

data class TableData(
    val dataTableModel: DataTableModel,
    val fieldViewModels: List<List<FieldViewModel>>,
    val cells: List<List<String>>,
    val accessDataWrite: Boolean,
    val showRowTotals: Boolean = false,
    val showColumnTotals: Boolean = false,
    val overriddenMeasure: TableMeasure,
    val hasDataElementDecoration: Boolean
) {
    fun columnHeaders() = dataTableModel.header
    fun catCombo() = dataTableModel.catCombo
    fun maxLengthLabel() = dataTableModel.rows?.maxLengthLabel()
    fun maxColumns() = dataTableModel.header!![dataTableModel.header.size - 1].size
    fun rows() = dataTableModel.rows
}

data class TableMeasure(val width: Int, val height: Int)
