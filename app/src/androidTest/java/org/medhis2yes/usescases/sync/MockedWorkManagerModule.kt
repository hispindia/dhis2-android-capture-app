package org.medhis2yes.usescases.sync

import androidx.work.WorkManager
import org.medhis2yes.data.service.workManager.WorkManagerController
import org.medhis2yes.data.service.workManager.WorkManagerModule

class MockedWorkManagerModule(private val mockedController: WorkManagerController) :
    WorkManagerModule() {

    override fun providesWorkManagerController(workManager: WorkManager): WorkManagerController {
        return mockedController
    }
}