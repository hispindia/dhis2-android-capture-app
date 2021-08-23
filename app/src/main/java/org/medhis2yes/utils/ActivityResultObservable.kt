package org.medhis2yes.utils

interface ActivityResultObservable {
    fun subscribe(activityResultObserver: ActivityResultObserver)
    fun unsubscribe()
}
