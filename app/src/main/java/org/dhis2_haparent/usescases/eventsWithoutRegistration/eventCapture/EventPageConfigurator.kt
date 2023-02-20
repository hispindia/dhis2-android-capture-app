package org.dhis2_haparent.usescases.eventsWithoutRegistration.eventCapture

import org.dhis2_haparent.utils.customviews.navigationbar.NavigationPageConfigurator

class EventPageConfigurator(
    private val eventCaptureRepository: EventCaptureContract.EventCaptureRepository
) : NavigationPageConfigurator {
    override fun displayDetails(): Boolean {
        return true
    }

    override fun displayDataEntry(): Boolean {
        return true
    }

    override fun displayAnalytics(): Boolean {
        return eventCaptureRepository.hasAnalytics()
    }

    override fun displayRelationships(): Boolean {
        return eventCaptureRepository.hasRelationships()
    }

    override fun displayNotes(): Boolean {
        return true
    }
}
