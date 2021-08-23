package org.medhis2yes.usescases.programEventDetail.eventMap

import org.medhis2yes.usescases.programEventDetail.ProgramEventMapData
import org.medhis2yes.usescases.programEventDetail.ProgramEventViewModel

interface EventMapFragmentView {
    fun setMap(mapData: ProgramEventMapData)
    fun updateEventCarouselItem(programEventViewModel: ProgramEventViewModel)
}
