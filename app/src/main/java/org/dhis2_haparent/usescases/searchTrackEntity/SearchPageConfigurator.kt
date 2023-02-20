package org.dhis2_haparent.usescases.searchTrackEntity

import org.dhis2_haparent.utils.customviews.navigationbar.NavigationPageConfigurator

class SearchPageConfigurator(
    val searchRepository: SearchRepository
) : NavigationPageConfigurator {

    private var canDisplayMap: Boolean = false
    private var canDisplayAnalytics: Boolean = false

    fun initVariables(): SearchPageConfigurator {
        canDisplayMap = searchRepository.programHasCoordinates()
        canDisplayAnalytics = searchRepository.programHasAnalytics()
        return this
    }

    override fun displayListView(): Boolean {
        return true
    }

    override fun displayTableView(): Boolean {
        return false
    }

    override fun displayMapView(): Boolean {
        return canDisplayMap
    }

    override fun displayAnalytics(): Boolean {
        return canDisplayAnalytics
    }
}
