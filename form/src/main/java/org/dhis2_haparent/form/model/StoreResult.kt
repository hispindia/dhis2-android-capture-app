package org.dhis2_haparent.form.model

data class StoreResult(
    val uid: String,
    val valueStoreResult: ValueStoreResult? = null,
    val valueStoreResultMessage: String? = null
)
