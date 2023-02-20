package org.dhis2_haparent.usescases.about

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.dhis2_haparent.Bindings.buildInfo
import org.dhis2_haparent.BuildConfig
import org.dhis2_haparent.R
import org.dhis2_haparent.usescases.BaseTest
import org.dhis2_haparent.usescases.main.MainActivity
import org.dhis2_haparent.usescases.main.homeRobot
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AboutTest : BaseTest() {

    @get:Rule
    val rule = ActivityTestRule(MainActivity::class.java, false, false)

    @Test
    fun shouldCheckVersionsWhenOpenAboutScreen() {
        startActivity()
        val appVersion = getAppVersionName()
        val sdkVersion = getSDKVersionName()

        homeRobot {
            clickOnNavigationDrawerMenu()
            clickAbout()
        }

        aboutRobot {
            checkVersionNames(appVersion, sdkVersion)
        }
    }

    private fun startActivity() {
        rule.launchActivity(null)
    }

    private fun getAppVersionName(): String {
        return context.buildInfo()
    }

    private fun getSDKVersionName() =
        String.format(context.getString(R.string.about_sdk), BuildConfig.SDK_VERSION)

}
