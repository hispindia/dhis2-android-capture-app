package org.medhis2yes.usescases.programEventDetail.eventList

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import org.medhis2yes.usescases.teiDashboard.dashboardfragments.teidata.teievents.EventViewModel

interface EventListFragmentView {
    fun setLiveData(pagedListLiveData: LiveData<PagedList<EventViewModel>>)
}
