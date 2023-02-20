package org.dhis2_haparent.maps.mapper

import org.dhis2_haparent.commons.data.EventViewModel
import org.dhis2_haparent.commons.data.SearchTeiModel
import org.dhis2_haparent.maps.model.EventUiComponentModel

class EventToEventUiComponent {
    fun mapList(
        events: List<EventViewModel>,
        teis: List<SearchTeiModel>
    ): List<EventUiComponentModel> {
        return events
            .filter { it.event?.geometry() != null }
            .map { event ->
                map(
                    event,
                    teis.first {
                        it.enrollments.any { enrollment ->
                            enrollment.uid() == event.event?.enrollment()
                        }
                    }
                )
            }
    }

    fun map(eventViewModel: EventViewModel, tei: SearchTeiModel): EventUiComponentModel {
        val eventUid = eventViewModel.event!!.uid()
        val lastUpdated = eventViewModel.lastUpdate

        return EventUiComponentModel(
            eventUid,
            eventViewModel.event!!,
            tei.enrollments.first { it.uid() == eventViewModel.event?.enrollment() },
            eventViewModel.stage,
            lastUpdated,
            tei.attributeValues,
            tei.profilePicturePath,
            tei.defaultTypeIcon,
            eventViewModel.orgUnitName
        )
    }
}
