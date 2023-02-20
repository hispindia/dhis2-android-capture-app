package org.dhis2_haparent.usescases.programEventDetail.eventMap

import org.dhis2_haparent.commons.data.ProgramEventViewModel
import org.dhis2_haparent.usescases.programEventDetail.ProgramEventMapData

interface EventMapFragmentView {
    fun setMap(mapData: ProgramEventMapData)
    fun updateEventCarouselItem(programEventViewModel: ProgramEventViewModel)
}
