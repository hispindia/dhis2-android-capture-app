package org.medhis2yes.usescases.teidashboard.robot

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withTagValue
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.medhis2yes.R
import org.medhis2yes.common.BaseRobot
import org.medhis2yes.common.matchers.RecyclerviewMatchers.Companion.atPosition
import org.medhis2yes.common.matchers.RecyclerviewMatchers.Companion.isNotEmpty
import org.medhis2yes.utils.dialFloatingActionButton.FAB_ID
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.equalTo

fun relationshipRobot(relationshipRobot: RelationshipRobot.() -> Unit) {
    RelationshipRobot().apply {
        relationshipRobot()
    }
}

class RelationshipRobot : BaseRobot() {
    fun clickOnFabAdd() {
        onView(withId(FAB_ID)).perform(click())
    }

    fun clickOnRelationshipType() {
        onView(
            withTagValue(
                equalTo(relationshipType)
            )
        ).perform(click())
    }

    fun checkRelationshipWasCreated(position: Int, tei: String) {
        onView(withId(R.id.relationship_recycler))
            .check(matches(
                allOf(
                    isDisplayed(), isNotEmpty(),
                    atPosition(
                        position, allOf(
                            hasDescendant(withText(relationshipType)),
                            hasDescendant(withText(tei))
                        )
                    )
                )
            ))
    }

    companion object {
        const val relationshipType = "Mother-Child_a-to-b_(Person-Person)"
    }
}
