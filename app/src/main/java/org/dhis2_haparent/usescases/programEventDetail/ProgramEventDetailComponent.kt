package org.dhis2_haparent.usescases.programEventDetail

import dagger.Subcomponent
import org.dhis2_haparent.commons.di.dagger.PerActivity
import org.dhis2_haparent.usescases.programEventDetail.eventList.EventListComponent
import org.dhis2_haparent.usescases.programEventDetail.eventList.EventListModule
import org.dhis2_haparent.usescases.programEventDetail.eventMap.EventMapComponent
import org.dhis2_haparent.usescases.programEventDetail.eventMap.EventMapModule

@PerActivity
@Subcomponent(modules = [ProgramEventDetailModule::class])
interface ProgramEventDetailComponent {
    fun inject(activity: ProgramEventDetailActivity)
    operator fun plus(eventListModule: EventListModule): EventListComponent
    operator fun plus(eventMapModule: EventMapModule): EventMapComponent
}
