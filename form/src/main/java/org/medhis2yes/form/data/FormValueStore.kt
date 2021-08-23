package org.medhis2yes.form.data

import org.medhis2yes.form.model.StoreResult

interface FormValueStore {

    fun save(uid: String, value: String?, extraData: String?): StoreResult
}
