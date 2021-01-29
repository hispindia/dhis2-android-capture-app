package org.dhis2hiv.utils.category

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import io.reactivex.Observable

interface CategoryDialogView {

    fun setTitle(dialogTitle: String)

    fun searchSource(): Observable<String>

    fun setLiveData(data: LiveData<PagedList<CategoryDialogItem>>)

    fun showDialog(): Boolean
}
