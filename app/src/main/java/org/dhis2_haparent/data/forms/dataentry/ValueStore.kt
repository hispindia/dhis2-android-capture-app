package org.dhis2_haparent.data.forms.dataentry

import io.reactivex.Flowable
import org.dhis2_haparent.form.model.StoreResult

interface ValueStore {
    fun save(uid: String, value: String?): Flowable<StoreResult>
    fun saveWithTypeCheck(uid: String, value: String?): Flowable<StoreResult>
    fun save(
        orgUnitUid: String,
        periodId: String,
        attributeOptionComboUid: String,
        dataElementUid: String,
        categoryOptionComboUid: String,
        value: String?
    ): Flowable<StoreResult>
    fun deleteOptionValues(optionCodeValuesToDelete: List<String>)
    fun deleteOptionValueIfSelected(field: String, optionUid: String): StoreResult
    fun deleteOptionValueIfSelectedInGroup(
        field: String,
        optionGroupUid: String,
        isInGroup: Boolean
    ): StoreResult

    fun overrideProgram(programUid: String?)
}
