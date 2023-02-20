package org.dhis2_haparent.usescases.main

import org.dhis2_haparent.utils.customviews.navigationbar.NavigationPageConfigurator

class HomePageConfigurator(
    private val homeRepository: HomeRepository
) : NavigationPageConfigurator {
    override fun displayTasks(): Boolean {
        return super.displayTasks()
    }

    override fun displayPrograms(): Boolean {
        return true
    }

    override fun displayAnalytics(): Boolean {
        return homeRepository.hasHomeAnalytics()
    }
}
