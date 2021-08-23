package org.medhis2yes.usescases.flow.searchFlow

import org.medhis2yes.common.BaseRobot
import org.medhis2yes.usescases.searchte.robot.filterRobot
import org.medhis2yes.usescases.searchte.robot.searchTeiRobot

fun searchFlowRobot(searchFlowRobot: SearchFlowRobot.() -> Unit) {
    SearchFlowRobot().apply {
        searchFlowRobot()
    }
}

class SearchFlowRobot : BaseRobot() {

    fun filterByOpenEnrollmentStatus(enrollmentStatus: String) {
        filterRobot {
            clickOnFilter()
            clickOnFilterBy(enrollmentStatus)
            clickOnFilterActiveOption()
            clickOnSortByField(enrollmentStatus)
        }
    }

    fun checkSearchCounters(searchCount: String, filterAtPositionCount: String, filter: String, filterTotalCount: String) {
        searchTeiRobot {
            checkFilterCount(searchCount)
        }

        filterRobot {
            checkFilterCounter(filterTotalCount)
            checkCountAtFilter(filter, filterAtPositionCount)
            closeSearchForm()
        }
    }

    fun checkTEIEnrollment() {
        filterRobot {
            checkTEIsAreOpen()
            checkTEINotSync()
        }
    }

}