package org.medhis2yes.usescases.splash

import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import org.medhis2yes.data.prefs.Preference
import org.medhis2yes.data.prefs.PreferenceProvider
import org.medhis2yes.data.schedulers.SchedulerProvider
import org.medhis2yes.data.server.UserManager
import org.medhis2yes.utils.Constants
import org.medhis2yes.utils.Constants.SERVER
import org.medhis2yes.utils.Constants.USER
import org.medhis2yes.utils.reporting.CrashReportController
import timber.log.Timber

class SplashPresenter internal constructor(
    private var view: SplashView,
    private val userManager: UserManager?,
    private val schedulerProvider: SchedulerProvider,
    private val preferenceProvider: PreferenceProvider,
    private val crashReportController: CrashReportController
) {

    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun destroy() {
        compositeDisposable.clear()
    }

    fun init() {
        preferenceProvider.sharedPreferences().all.forEach {
            when (it.key) {
                Constants.PREFS_URLS, Constants.PREFS_USERS -> {
                }
                else -> preferenceProvider.setValue(it.key, it.value)
            }
        }
        isUserLoggedIn()
    }

    private fun isUserLoggedIn() {
        userManager?.let { userManager ->
            compositeDisposable.add(
                userManager.isUserLoggedIn
                    .delay(2000, TimeUnit.MILLISECONDS, schedulerProvider.io())
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(
                        { userLogged ->
                            if (userLogged) {
                                trackUserInfo()
                            }
                            view.goToNextScreen(
                                userLogged,
                                preferenceProvider.getBoolean(Preference.SESSION_LOCKED, false),
                                preferenceProvider.getBoolean(Preference.INITIAL_SYNC_DONE, false)
                            )
                        },
                        { Timber.d(it) }
                    )
            )
        } ?: view.goToNextScreen(false, sessionLocked = false, initialSyncDone = false)
    }

    private fun trackUserInfo() {
        val username = preferenceProvider.getString(USER)
        val server = preferenceProvider.getString(SERVER)

        crashReportController.trackServer(server)
        crashReportController.trackUser(username, server)
    }
}
