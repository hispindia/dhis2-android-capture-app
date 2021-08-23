package org.medhis2yes.usescases.filters

import androidx.test.rule.ActivityTestRule
import org.medhis2yes.common.filters.filterRobotCommon
import org.medhis2yes.usescases.BaseTest
import org.medhis2yes.usescases.flow.syncFlow.robot.eventWithoutRegistrationRobot
import org.medhis2yes.usescases.form.formRobot
import org.medhis2yes.usescases.main.MainActivity
import org.medhis2yes.usescases.main.homeRobot
import org.medhis2yes.usescases.teidashboard.robot.eventRobot
import org.junit.Rule
import org.junit.Test

class FilterTest: BaseTest() {

    @get:Rule
    val rule = ActivityTestRule(MainActivity::class.java, false, false)


    @Test
    fun checkFromToDateFilter() {
        setupCredentials()
        startActivity()

        homeRobot {
            openFilters()
        }

        filterRobotCommon {
            openFilterAtPosition(0)
            clickOnFromToDateOption()
            selectDate(2020,6,15)
            acceptDateSelected()
            selectDate(2020,11,7)
            acceptDateSelected()
        }
        homeRobot {
            openFilters()
            checkItemsInProgram(4,"Child Programme", "3")
            checkItemsInProgram(6, "Contraceptives Voucher Program", "5")
            checkItemsInProgram(27, "Mortality < 5 years", "4")
        }
    }

    @Test
    fun checkWritingOrgUnitFilter() {
        setupCredentials()
        startActivity()

        homeRobot {
            openFilters()
        }

        filterRobotCommon {
            openFilterAtPosition(1)
            typeOrgUnit("OU TEST PARENT")
            clickAddOrgUnit()
            closeKeyboard()
        }
        homeRobot {
            openFilters()
            checkItemsInProgram(4,"Child Programme", "0")
            checkItemsInProgram(43, "XX TEST EVENT FULL", "2")
            checkItemsInProgram(45, "XX TEST TRACKER PROGRAM", "4")
        }
    }

    @Test
    fun checkTreeOrgUnitFilter(){
        startActivity()
        setupCredentials()

        homeRobot {
            openFilters()
        }

        filterRobotCommon {
            openFilterAtPosition(1)
            clickOnOrgUnitTree()
            selectTreeOrgUnit("OU TEST PARENT")
            returnToSearch()
        }
        homeRobot {
            openFilters()
            checkItemsInProgram(4,"Child Programme", "0")
            checkItemsInProgram(43, "XX TEST EVENT FULL", "2")
            checkItemsInProgram(45, "XX TEST TRACKER PROGRAM", "4")
        }
    }

    @Test
    fun checkSyncFilter() {
        setupCredentials()
        startActivity()

        homeRobot {
            openProgramByPosition(0)
        }
        eventWithoutRegistrationRobot {
            clickOnEventAtPosition(0)
        }
        eventRobot {
            clickOnFormFabButton()
            clickOnFinishAndComplete()
            pressBack()
        }
        homeRobot {
            openFilters()
        }
        filterRobotCommon {
            openFilterAtPosition(2)
            selectNotSyncedState()
        }
        homeRobot {
            checkItemsInProgram(0,"Antenatal care visit", "1")
            checkItemsInProgram(4,"Child Programme", "0")
        }
    }

    @Test
    fun checkCombinedFilters() {
        setupCredentials()
        startActivity()

        homeRobot {
            openProgramByPosition(41)
        }
        eventWithoutRegistrationRobot {
            clickOnEventAtPosition(0)
        }
        formRobot {
            clickOnSelectOption("ZZ TEST RULE ACTIONS A", 1,"Hide Field", 1)
            pressBack()
            pressBack()
            pressBack()
        }
        homeRobot {
            openFilters()
        }

        filterRobotCommon {
            openFilterAtPosition(1)
            typeOrgUnit("OU TEST PARENT")
            clickAddOrgUnit()
            closeKeyboard()
            openFilterAtPosition(2)
            selectNotSyncedState()
        }
        homeRobot {
            openFilters()
            checkItemsInProgram(37,"TB program", "0")
            checkItemsInProgram(41, "XX TEST EVENT FULL", "1")
        }
    }



    private fun startActivity() {
        rule.launchActivity(null)
    }
}