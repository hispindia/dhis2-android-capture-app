package org.medhis2yes.form.data

import org.medhis2yes.form.model.FieldUiModel
import org.medhis2yes.form.model.RowAction
import org.medhis2yes.form.model.StoreResult

interface FormRepository {

    fun processUserAction(action: RowAction): StoreResult

    fun composeList(list: List<FieldUiModel>? = null): List<FieldUiModel>

    fun <E> Iterable<E>.updated(index: Int, elem: E): List<E> =
        mapIndexed { i, existing -> if (i == index) elem else existing }
}
