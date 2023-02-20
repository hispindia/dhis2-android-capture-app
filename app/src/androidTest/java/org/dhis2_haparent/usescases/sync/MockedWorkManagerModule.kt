package org.dhis2_haparent.usescases.sync

import androidx.work.WorkManager
import org.dhis2_haparent.data.service.workManager.WorkManagerController
import org.dhis2_haparent.data.service.workManager.WorkManagerModule

class MockedWorkManagerModule(private val mockedController: WorkManagerController) :
    WorkManagerModule() {

    override fun providesWorkManagerController(workManager: WorkManager): WorkManagerController {
        return mockedController
    }
}