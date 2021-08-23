package org.medhis2yes.usescases.flow.syncFlow.robot

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.medhis2yes.R
import org.medhis2yes.common.BaseRobot
import org.medhis2yes.usescases.datasets.datasetDetail.DataSetDetailViewHolder

fun dataSetRobot(dataSetRobot: DataSetRobot.() -> Unit) {
    DataSetRobot().apply {
        dataSetRobot()
    }
}

class DataSetRobot : BaseRobot() {

    fun clickOnDataSetAtPosition(position: Int) {
        onView(withId(R.id.recycler))
            .perform(
                actionOnItemAtPosition<DataSetDetailViewHolder>(position, click())
            )
    }

    fun clickOnSave() {
        onView(withId(R.id.saveButton)).perform(click())
    }

}