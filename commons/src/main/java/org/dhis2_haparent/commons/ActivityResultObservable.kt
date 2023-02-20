package org.dhis2_haparent.commons

interface ActivityResultObservable {
    fun subscribe(activityResultObserver: ActivityResultObserver)
    fun unsubscribe()
}
