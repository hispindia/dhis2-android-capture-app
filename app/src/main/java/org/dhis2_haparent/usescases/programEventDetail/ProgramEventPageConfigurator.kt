package org.dhis2_haparent.usescases.programEventDetail

import org.dhis2_haparent.utils.customviews.navigationbar.NavigationPageConfigurator

class ProgramEventPageConfigurator(
    val repository: ProgramEventDetailRepository
) : NavigationPageConfigurator {

    override fun displayListView(): Boolean {
        return true
    }

    override fun displayMapView(): Boolean {
        return repository.programHasCoordinates()
    }

    override fun displayAnalytics(): Boolean {
        return repository.programHasAnalytics()
    }
}
