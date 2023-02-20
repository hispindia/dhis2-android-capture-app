package org.dhis2_haparent.usescases

import org.dhis2_haparent.usescases.about.AboutTest
import org.dhis2_haparent.usescases.datasets.DataSetTest
import org.dhis2_haparent.usescases.enrollment.EnrollmentTest
import org.dhis2_haparent.usescases.event.EventTest
import org.dhis2_haparent.usescases.filters.FilterTest
import org.dhis2_haparent.usescases.jira.JiraTest
import org.dhis2_haparent.usescases.login.LoginTest
import org.dhis2_haparent.usescases.main.MainTest
import org.dhis2_haparent.usescases.pin.PinTest
import org.dhis2_haparent.usescases.programevent.ProgramEventTest
import org.dhis2_haparent.usescases.searchte.SearchTETest
import org.dhis2_haparent.usescases.settings.SettingsTest
import org.dhis2_haparent.usescases.sync.SyncActivityTest
import org.dhis2_haparent.usescases.teidashboard.TeiDashboardTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    AboutTest::class,
    DataSetTest::class,
    EnrollmentTest::class,
    EventTest::class,
    FilterTest::class,
    JiraTest::class,
    LoginTest::class,
    MainTest::class,
    PinTest::class,
    ProgramEventTest::class,
    SearchTETest::class,
    SettingsTest::class,
    SyncActivityTest::class,
    TeiDashboardTest::class
)
class UseCaseTestsSuite
