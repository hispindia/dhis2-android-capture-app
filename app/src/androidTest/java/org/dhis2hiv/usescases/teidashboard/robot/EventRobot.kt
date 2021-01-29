package org.dhis2hiv.usescases.teidashboard.robot

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.dhis2hiv.R
import org.dhis2hiv.common.BaseRobot
import org.dhis2hiv.common.viewactions.clickChildViewWithId
import org.dhis2hiv.common.viewactions.scrollToBottomRecyclerView
import org.dhis2hiv.common.viewactions.typeChildViewWithId
import org.dhis2hiv.data.forms.dataentry.fields.edittext.EditTextCustomHolder
import org.dhis2hiv.usescases.teiDashboard.dashboardfragments.teidata.DashboardProgramViewHolder

fun eventRobot(eventRobot: EventRobot.() -> Unit) {
    EventRobot().apply {
        eventRobot()
    }
}

class EventRobot : BaseRobot() {

    fun scrollToBottomForm() {
        onView(withId(R.id.formRecycler)).perform(scrollToBottomRecyclerView())
    }

    fun clickOnFormFabButton() {
        onView(withId(R.id.actionButton)).perform(click())
    }
    fun clickOnFinish() {
        onView(withId(R.id.finish)).perform(click())
    }

    fun clickOnFinishAndComplete() {
        onView(withId(R.id.complete)).perform(click())
    }

    fun fillRadioButtonForm(numberFields: Int) {
        var formLength = 0

        while (formLength < numberFields) {
            onView(withId(R.id.formRecycler))
                .perform(
                    actionOnItemAtPosition<DashboardProgramViewHolder>(
                        formLength,
                        clickChildViewWithId(R.id.yes)
                    )
                )
            formLength++
        }
    }

    fun clickOnChangeDate() {
        onView(withText(R.string.change_event_date)).perform(click())
    }

    fun clickOnEditDate() {
        onView(withId(R.id.date)).perform(click())
    }

    fun acceptUpdateEventDate() {
        onView(withId(R.id.acceptButton)).perform(click())
    }

    fun clickOnUpdate() {
        onView(withId(R.id.action_button)).perform(click())
    }

    fun typeOnRequiredEventForm(text: String, position: Int) {
        onView(withId(R.id.formRecycler))
            .perform(
                actionOnItemAtPosition<EditTextCustomHolder>( //EditTextCustomHolder
                    position, typeChildViewWithId(text, R.id.input_editText)
                )
            )
    }

    fun clickOnFutureAlertDialog(){
        clickOnChangeDate()
        clickOnEditDate()
        acceptUpdateEventDate()
        clickOnUpdate()
    }
}
