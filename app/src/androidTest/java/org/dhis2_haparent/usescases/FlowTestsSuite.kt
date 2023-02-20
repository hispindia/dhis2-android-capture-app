package org.dhis2_haparent.usescases

import org.dhis2_haparent.usescases.flow.searchFlow.SearchFlowTest
import org.dhis2_haparent.usescases.flow.syncFlow.SyncFlowTest
import org.dhis2_haparent.usescases.flow.teiFlow.TeiFlowTest
import org.dhis2_haparent.usescases.form.FormTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    SearchFlowTest::class,
    SyncFlowTest::class,
    TeiFlowTest::class,
    FormTest::class
)
class FlowTestsSuite
