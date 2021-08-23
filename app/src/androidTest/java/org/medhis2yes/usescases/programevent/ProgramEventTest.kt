package org.medhis2yes.usescases.programevent

import android.Manifest
import android.content.Intent
import androidx.test.rule.ActivityTestRule
import org.medhis2yes.AppTest.Companion.DB_TO_IMPORT
import org.medhis2yes.usescases.BaseTest
import org.medhis2yes.usescases.event.eventRegistrationRobot
import org.medhis2yes.usescases.programEventDetail.ProgramEventDetailActivity
import org.medhis2yes.usescases.programevent.robot.programEventsRobot
import org.medhis2yes.usescases.teidashboard.robot.eventRobot
import org.junit.Rule
import org.junit.Test

class ProgramEventTest: BaseTest() {

    private val atenatalCare = "lxAQ7Zs9VYR"
    private val informationCampaign = "q04UBOqq3rp"

    @get:Rule
    val rule = ActivityTestRule(ProgramEventDetailActivity::class.java, false, false)


    override fun getPermissionsToBeAccepted(): Array<String> {
        return arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    @Test
    fun shouldCreateNewEventAndCompleteIt() {
        val eventOrgUnit = "Ngelehun CHC"
        prepareProgramAndLaunchActivity(atenatalCare)

        programEventsRobot {
            clickOnAddEvent()
        }

        eventRegistrationRobot {
            clickNextButton()
            waitToDebounce(600)
        }
        eventRobot {
            clickOnFormFabButton()
            clickOnFinishAndComplete()
        }

        programEventsRobot {
            checkEventWasCreatedAndClosed(eventOrgUnit, 0)
        }

    }
    @Test
    fun shouldOpenExistingEvent() {
        val eventDate = "15/3/2020"
        val eventOrgUnit = "Ngelehun CHC"

        prepareProgramAndLaunchActivity(atenatalCare)

        programEventsRobot {
            clickOnEvent(eventDate, eventOrgUnit)
        }

        eventRobot {
            checkDetails(eventDate, eventOrgUnit)
        }
    }

    @Test
    fun shouldCompleteAnEventAndReopenIt() {
        val eventDate = "15/3/2020"
        val eventOrgUnit = "Ngelehun CHC"

        prepareProgramAndLaunchActivity(atenatalCare)

        programEventsRobot {
            clickOnEvent(eventDate, eventOrgUnit)
        }

        eventRobot {
            clickOnFormFabButton()
            clickOnFinishAndComplete()
        }

        programEventsRobot {
            checkEventIsComplete(eventDate, eventOrgUnit)
            clickOnEvent(eventDate, eventOrgUnit)
        }

        eventRobot {
            clickOnFormFabButton()
            clickOnReopen()
            pressBack()
        }

        programEventsRobot {
            waitToDebounce(800)
            checkEventIsOpen(eventDate, eventOrgUnit)
        }

    }

    @Test
    fun shouldOpenDetailsOfExistingEvent() {
        val eventDate = "15/3/2020"
        val eventOrgUnit = "Ngelehun CHC"

        prepareProgramAndLaunchActivity(atenatalCare)

        programEventsRobot {
            clickOnEvent(eventDate, eventOrgUnit)
        }
        eventRobot {
            clickOnDetails()
            checkEventDetails(eventDate, eventOrgUnit)
        }
    }

    @Test
    fun shouldDeleteEvent() {
        val eventDate = "15/3/2020"
        val eventOrgUnit = "Ngelehun CHC"

        prepareProgramAndLaunchActivity(atenatalCare)

        programEventsRobot {
            clickOnEvent(eventDate, eventOrgUnit)
        }
        eventRobot {
            openMenuMoreOptions()
            clickOnDelete()
            clickOnDeleteDialog()
        }
        programEventsRobot {
            checkEventWasDeleted(eventDate, eventOrgUnit)
        }
        rule.activity.application.deleteDatabase(DB_TO_IMPORT)
    }

    @Test
    fun shouldOpenEventAndShowMap() {

        prepareProgramAndLaunchActivity(informationCampaign)

        programEventsRobot {
            clickOnMap()
            checkMapIsDisplayed()
        }
    }

    private fun prepareProgramAndLaunchActivity(programUid: String) {
        Intent().apply {
            putExtra(ProgramEventDetailActivity.EXTRA_PROGRAM_UID, programUid)
        }.also { rule.launchActivity(it) }
    }
}