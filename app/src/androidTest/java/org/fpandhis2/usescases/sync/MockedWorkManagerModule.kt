package org.fpandhis2.usescases.sync

import androidx.work.WorkManager
import org.fpandhis2.data.service.workManager.WorkManagerController
import org.fpandhis2.data.service.workManager.WorkManagerModule

class MockedWorkManagerModule(private val mockedController: WorkManagerController) :
    WorkManagerModule() {

    override fun providesWorkManagerController(workManager: WorkManager): WorkManagerController {
        return mockedController
    }
}