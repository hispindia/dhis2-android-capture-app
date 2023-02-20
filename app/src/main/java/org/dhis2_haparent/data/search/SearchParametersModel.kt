package org.dhis2_haparent.data.search

import org.hisp.dhis.android.core.program.Program

data class SearchParametersModel(
    val selectedProgram: Program?,
    val queryData: MutableMap<String, String>?
) {
    fun copy(): SearchParametersModel = copy(
        queryData = hashMapOf<String, String>().apply { queryData?.let { putAll(it) } }
    )
}
