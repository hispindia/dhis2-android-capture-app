package org.dhis2_haparent.usescases.programEventDetail.eventList

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import org.dhis2_haparent.commons.data.EventViewModel

interface EventListFragmentView {
    fun setLiveData(pagedListLiveData: LiveData<PagedList<EventViewModel>>)
}
