package org.fpandhis2.usescases.flow.teiFlow


import org.fpandhis2.common.BaseRobot
import org.fpandhis2.usescases.searchte.searchTeiRobot
import org.fpandhis2.usescases.flow.teiFlow.entity.EnrollmentListUIModel
import org.fpandhis2.usescases.flow.teiFlow.entity.RegisterTEIUIModel
import org.fpandhis2.usescases.teidashboard.robot.enrollmentRobot
import org.fpandhis2.usescases.teidashboard.robot.teiDashboardRobot

fun teiFlowRobot(teiFlowRobot: TeiFlowRobot.() -> Unit) {
    TeiFlowRobot().apply {
        teiFlowRobot()
    }
}

class TeiFlowRobot : BaseRobot() {

    fun registerTEI(registrationModel: RegisterTEIUIModel) {
        val registrationDate = registrationModel.firstSpecificDate
        val enrollmentDate = registrationModel.enrollmentDate

        searchTeiRobot {
            typeAttributeAtPosition(registrationModel.name, 0)
            typeAttributeAtPosition(registrationModel.lastName, 1)
            clickOnDateField()
            selectSpecificDate(registrationDate.year,registrationDate.month,registrationDate.day)
            acceptDate()
            clickOnFab()
            clickOnFab()
            selectSpecificDate(enrollmentDate.year,enrollmentDate.month,enrollmentDate.day)
            acceptDate()
        }

        enrollmentRobot {
            clickOnSaveEnrollment()
        }
    }

    fun enrollToProgram(program: String) {
        teiDashboardRobot {
            clickOnMenuMoreOptions()
            clickOnMenuProgramEnrollments()
        }

        enrollmentRobot {
            clickOnSameProgramForEnrollment(program)
            clickOnAcceptEnrollmentDate()
            waitToDebounce(5000)
            scrollToBottomProgramForm()
            clickOnSaveEnrollment()
        }
    }

    fun checkActiveAndPastEnrollmentDetails(enrollmentDetails: EnrollmentListUIModel) {
        teiDashboardRobot {
            clickOnMenuMoreOptions()
            clickOnMenuProgramEnrollments()
        }

        enrollmentRobot {
            checkActiveAndPastEnrollmentDetails(enrollmentDetails)
        }
    }

    fun checkPastEventsAreClosed(totalEvents: Int, programPosition: Int) {
        enrollmentRobot {
            clickOnEnrolledProgram(programPosition)
        }

        teiDashboardRobot {
            checkLockCompleteIconIsDisplay()
            checkCanNotAddEvent()
            checkAllEventsAreClosed(totalEvents)
        }
    }

    fun closeEnrollmentAndCheckEvents(totalEvents: Int) {
        teiDashboardRobot {
            clickOnMenuMoreOptions()
            clickOnTimelineEvents()
            clickOnMenuMoreOptions()
            clickOnMenuComplete()
            checkCanNotAddEvent()
            checkAllEventsAreClosed(totalEvents)
        }
    }
}
