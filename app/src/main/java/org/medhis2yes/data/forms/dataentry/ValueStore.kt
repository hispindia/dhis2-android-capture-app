package org.medhis2yes.data.forms.dataentry

import io.reactivex.Flowable
import org.medhis2yes.form.model.StoreResult
import org.medhis2yes.usescases.datasets.dataSetTable.DataSetTableModel

interface ValueStore {
    fun save(uid: String, value: String?): Flowable<StoreResult>
    fun saveWithTypeCheck(uid: String, value: String?): Flowable<StoreResult>
    fun save(dataValue: DataSetTableModel): Flowable<StoreResult>
    fun deleteOptionValues(optionCodeValuesToDelete: List<String>)
    fun deleteOptionValueIfSelected(field: String, optionUid: String): StoreResult
    fun deleteOptionValueIfSelectedInGroup(
        field: String,
        optionGroupUid: String,
        isInGroup: Boolean
    ): StoreResult
}
