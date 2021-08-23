package org.medhis2yes.usescases.enrollment

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.medhis2yes.R
import org.medhis2yes.common.BaseRobot
import org.medhis2yes.common.viewactions.clickChildViewWithId
import org.medhis2yes.usescases.teiDashboard.dashboardfragments.teidata.DashboardProgramViewHolder
import org.medhis2yes.usescases.teidashboard.robot.EnrollmentRobot
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.equalTo


fun enrollmentFormRobot(enrollmentFormRobot: EnrollmentFormRobot.() -> Unit) {
    EnrollmentFormRobot().apply {
        enrollmentFormRobot()
    }
}

class EnrollmentFormRobot : BaseRobot() {

    fun clickOnDateOfBirth() {
        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.actionOnItem<DashboardProgramViewHolder>(
                    ViewMatchers.hasDescendant(withText(containsString(EnrollmentRobot.DATE_OF_BIRTH))),
                    clickChildViewWithId(R.id.inputEditText)
                )
            )
    }

    fun changePickerDate() {
        onView(withId(equalTo(R.id.widget_datepicker))).perform(PickerActions.setDate(2020, 1, 1))
    }

    fun clickOnAcceptEnrollmentDate() {
        onView(withId(R.id.acceptButton)).perform(ViewActions.click())
    }

    fun checkDateWarningIsDisplayed() {
        onView(withText(R.string.enrollment_date_edition_warning)).check(matches(isDisplayed()))
    }
}