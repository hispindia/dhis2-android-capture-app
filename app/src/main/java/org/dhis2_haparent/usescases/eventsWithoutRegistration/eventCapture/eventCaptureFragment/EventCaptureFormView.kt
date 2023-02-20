package org.dhis2_haparent.usescases.eventsWithoutRegistration.eventCapture.eventCaptureFragment

interface EventCaptureFormView {
    fun performSaveClick()
    fun hideSaveButton()
    fun showSaveButton()
    fun onReopen()
}
