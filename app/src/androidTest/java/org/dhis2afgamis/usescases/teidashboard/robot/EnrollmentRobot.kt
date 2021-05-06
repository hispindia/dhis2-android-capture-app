package org.dhis2afgamis.usescases.teidashboard.robot

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollTo
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.dhis2afgamis.R
import org.dhis2afgamis.common.BaseRobot
import org.dhis2afgamis.common.matchers.RecyclerviewMatchers.Companion.atPosition
import org.dhis2afgamis.common.viewactions.clickChildViewWithId
import org.dhis2afgamis.common.viewactions.scrollToBottomRecyclerView
import org.dhis2afgamis.common.viewactions.typeChildViewWithId
import org.dhis2afgamis.data.forms.dataentry.fields.edittext.EditTextCustomHolder
import org.dhis2afgamis.usescases.searchTrackEntity.adapters.SearchTEViewHolder
import org.dhis2afgamis.usescases.teiDashboard.dashboardfragments.teidata.DashboardProgramViewHolder
import org.dhis2afgamis.usescases.flow.teiFlow.entity.EnrollmentListUIModel
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.containsString

fun enrollmentRobot(enrollmentRobot: EnrollmentRobot.() -> Unit) {
    EnrollmentRobot().apply {
        enrollmentRobot()
    }
}

class EnrollmentRobot : BaseRobot() {

    fun clickOnAProgramForEnrollment(program: String) {
        onView(withId(R.id.recycler))
            .perform(
                scrollTo<SearchTEViewHolder>(hasDescendant(withText(program))),
                actionOnItem<DashboardProgramViewHolder>(hasDescendant(withText(program)), clickChildViewWithId(R.id.action_button))
            )
    }

    fun clickOnSameProgramForEnrollment(program: String) {
        onView(withId(R.id.recycler))
            .perform(
                scrollTo<SearchTEViewHolder>(allOf(hasDescendant(withText(program)), hasDescendant(
                    withText(ENROLL)))),
                actionOnItem<DashboardProgramViewHolder>(allOf(hasDescendant(withText(program)), hasDescendant(
                    withText(ENROLL))), clickChildViewWithId(R.id.action_button))
            )
    }

    fun clickOnAcceptEnrollmentDate() {
        onView(withId(R.id.acceptButton)).perform(click())
    }

    fun clickOnSaveEnrollment() {
        onView(withId(R.id.save)).perform(click())
    }

    fun clickOnPersonAttributes(attribute: String) {
        onView(withId(R.id.fieldRecycler))
            .perform(actionOnItem<EditTextCustomHolder>(
                hasDescendant(withText(containsString(attribute))), click()))
    }

    fun typeOnRequiredTextField(text: String, position: Int) {
        onView(withId(R.id.fieldRecycler))
            .perform(
                actionOnItemAtPosition<EditTextCustomHolder>(
                    position, typeChildViewWithId(text, R.id.input_editText))
            )
    }

    fun scrollToBottomProgramForm() {
        onView(withId(R.id.fieldRecycler)).perform(scrollToBottomRecyclerView())
    }

    fun clickOnCalendarItem() {
        onView(withId(R.id.fieldRecycler))
            .perform(actionOnItem<DashboardProgramViewHolder>(
                hasDescendant(withText(DATE_OF_BIRTH)), clickChildViewWithId(R.id.inputEditText)))
    }

    fun checkActiveAndPastEnrollmentDetails(enrollmentListUIModel: EnrollmentListUIModel) {
        checkHeaderAndProgramDetails(enrollmentListUIModel, ACTIVE_PROGRAMS, 1, 2, enrollmentListUIModel.currentEnrollmentDate)
        checkHeaderAndProgramDetails(enrollmentListUIModel, PAST_PROGRAMS, 3, 4, enrollmentListUIModel.pastEnrollmentDate)
    }

    private fun checkHeaderAndProgramDetails(enrollmentListUIModel: EnrollmentListUIModel, programStatus: String, headerPosition: Int, programPosition: Int, enrollmentDay: String) {
        onView(withId(R.id.recycler)).check(matches(atPosition(headerPosition, withText(programStatus))))
        onView(withId(R.id.recycler)).check(matches(allOf(atPosition(programPosition, allOf(
            hasDescendant(withText(enrollmentListUIModel.program)),
            hasDescendant(withText(enrollmentListUIModel.orgUnit)),
            hasDescendant(withText(enrollmentDay))
        )))))
    }

    fun clickOnEnrolledProgram(position: Int) {
        onView(withId(R.id.recycler))
            .perform(
                actionOnItemAtPosition<DashboardProgramViewHolder>(position, click())
            )
    }

    companion object {
        const val ACTIVE_PROGRAMS = "Active programs"
        const val PAST_PROGRAMS = "Past programs"
        const val ENROLL = "ENROLL"
        const val DATE_OF_BIRTH = "Date of birth*"
    }
}