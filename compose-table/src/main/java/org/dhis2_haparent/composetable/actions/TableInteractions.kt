package org.dhis2_haparent.composetable.actions

import org.dhis2_haparent.composetable.model.TableCell
import org.dhis2_haparent.composetable.model.TableDialogModel
import org.dhis2_haparent.composetable.ui.TableSelection

interface TableInteractions {
    fun onSelectionChange(newTableSelection: TableSelection) = run { }
    fun onDecorationClick(dialogModel: TableDialogModel) = run { }
    fun onClick(tableCell: TableCell) = run { }
    fun onRowHeaderSizeChanged(widthDpValue: Float) = run { }
    fun onOptionSelected(cell: TableCell, code: String, label: String) = run { }
}
