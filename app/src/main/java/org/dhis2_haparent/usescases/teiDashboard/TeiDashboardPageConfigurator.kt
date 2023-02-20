package org.dhis2_haparent.usescases.teiDashboard

import org.dhis2_haparent.utils.customviews.navigationbar.NavigationPageConfigurator

class TeiDashboardPageConfigurator(
    val dashboardRepository: DashboardRepository,
    val isPortrait: Boolean
) : NavigationPageConfigurator {

    override fun displayDetails(): Boolean {
        return isPortrait
    }

    override fun displayAnalytics(): Boolean {
        return dashboardRepository.programHasAnalytics()
    }

    override fun displayRelationships(): Boolean {
        return dashboardRepository.programHasRelationships()
    }

    override fun displayNotes(): Boolean {
        return true
    }
}
