package org.fpandhis2.usescases.sync

import androidx.lifecycle.LiveData
import androidx.work.WorkInfo
import io.reactivex.disposables.CompositeDisposable
import org.fpandhis2.data.prefs.Preference
import org.fpandhis2.data.prefs.PreferenceProvider
import org.fpandhis2.data.schedulers.SchedulerProvider
import org.fpandhis2.data.service.METADATA_MESSAGE
import org.fpandhis2.data.service.workManager.WorkManagerController
import org.fpandhis2.data.service.workManager.WorkerItem
import org.fpandhis2.data.service.workManager.WorkerType
import org.fpandhis2.utils.Constants
import timber.log.Timber

class SyncPresenter internal constructor(
    private val view: SyncView,
    private val syncRepository: SyncRepository,
    private val schedulerProvider: SchedulerProvider,
    private val workManagerController: WorkManagerController,
    private val preferences: PreferenceProvider
) {
    private val disposable = CompositeDisposable()

    fun sync() {
        workManagerController
            .syncDataForWorkers(Constants.META_NOW, Constants.DATA_NOW, Constants.INITIAL_SYNC)
    }

    fun observeSyncProcess(): LiveData<List<WorkInfo>> {
        return workManagerController.getWorkInfosForUniqueWorkLiveData(Constants.INITIAL_SYNC)
    }

    fun handleSyncInfo(workInfoList: List<WorkInfo>) {
        workInfoList.forEach { workInfo ->
            if (workInfo.tags.contains(Constants.META_NOW)) {
                handleMetaState(workInfo.state, workInfo.outputData.getString(METADATA_MESSAGE))
            } else if (workInfo.tags.contains(Constants.DATA_NOW)) {
                handleDataState(workInfo.state)
            }
        }
    }

    private fun handleMetaState(state: WorkInfo.State, message: String?) {
        when (state) {
            WorkInfo.State.RUNNING -> view.setMetadataSyncStarted()
            WorkInfo.State.SUCCEEDED -> view.setMetadataSyncSucceed()
            WorkInfo.State.FAILED -> view.showMetadataFailedMessage(message)
            else -> {
            }
        }
    }

    private fun handleDataState(state: WorkInfo.State) {
        when (state) {
            WorkInfo.State.RUNNING -> view.setDataSyncStarted()
            WorkInfo.State.SUCCEEDED -> view.setDataSyncSucceed()
            else -> {
            }
        }
    }

    fun onMetadataSyncSuccess() {
        disposable.add(
            syncRepository.getTheme().doOnSuccess { flagAndTheme ->
                preferences.setValue(Preference.FLAG, flagAndTheme.first)
                preferences.setValue(Preference.THEME, flagAndTheme.second)
            }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    { (first, second) ->
                        view.setFlag(first)
                        view.setServerTheme(second)
                    },
                    { t: Throwable? ->
                        Timber.e(t)
                    }
                )
        )
    }

    fun onDataSyncSuccess() {
        preferences.setValue(Preference.INITIAL_SYNC_DONE, true)
        val workerItem = WorkerItem(
            Constants.RESERVED,
            WorkerType.RESERVED,
            null,
            null,
            null,
            null
        )
        workManagerController.cancelAllWorkByTag(workerItem.workerName)
        workManagerController.syncDataForWorker(workerItem)
        view.goToMain()
    }

    fun onLogout() {
        disposable.add(
            syncRepository.logout()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    { view.goToLogin() }
                ) { t: Throwable? ->
                    Timber.e(t)
                }
        )
    }

    fun onDetach() {
        disposable.clear()
    }
}
