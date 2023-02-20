package org.dhis2_haparent.data.forms.dataentry

interface SearchTEIRepository {
    fun isUniqueTEIAttributeOnline(
        uid: String,
        value: String?,
        teiUid: String,
        programUid: String?
    ): Boolean
}
