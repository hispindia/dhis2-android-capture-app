package org.fpandhis2.usescases.sync

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.fpandhis2.R
import org.fpandhis2.common.BaseRobot
import org.fpandhis2.usescases.main.MainActivity
import org.hamcrest.CoreMatchers

fun syncRobot(syncRobot: SyncRobot.() -> Unit) {
    SyncRobot().apply {
        syncRobot()
    }
}

class SyncRobot : BaseRobot() {
    fun checkMetadataErrorDialogIsDisplayed() {
        onView(withText(R.string.metada_first_sync_error)).check(matches(isDisplayed()))
    }

    fun checkMetadataIsSyncing(){
        onView(withId(R.id.metadataText)).check(matches(withText(R.string.syncing_configuration)))
    }

    fun checkMetadataIsReady(){
        onView(withId(R.id.metadataText)).check(matches(withText(R.string.configuration_ready)))
    }

    fun checkDataIsWaiting(){
        onView(withId(R.id.eventsText)).check(matches(withText(R.string.syncing_data_shortly)))
    }

    fun checkDataIsSyncing(){
        onView(withId(R.id.eventsText)).check(matches(withText(R.string.syncing_data)))
    }

    fun checkDataIsReady(){
        onView(withId(R.id.eventsText)).check(matches(withText(R.string.data_ready)))
    }

    fun checkMainActivityIsLaunched(){
        Intents.intended(CoreMatchers.allOf(IntentMatchers.hasComponent(MainActivity::class.java.name)))
    }
}