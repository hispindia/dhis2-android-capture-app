package org.dhis2afgamis.usescases.sync

import androidx.work.WorkManager
import org.dhis2afgamis.data.service.workManager.WorkManagerController
import org.dhis2afgamis.data.service.workManager.WorkManagerModule

class MockedWorkManagerModule(private val mockedController: WorkManagerController) :
    WorkManagerModule() {

    override fun providesWorkManagerController(workManager: WorkManager): WorkManagerController {
        return mockedController
    }
}