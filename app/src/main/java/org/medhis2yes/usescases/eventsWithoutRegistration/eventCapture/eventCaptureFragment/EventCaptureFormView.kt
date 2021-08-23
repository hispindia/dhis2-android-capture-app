package org.medhis2yes.usescases.eventsWithoutRegistration.eventCapture.eventCaptureFragment

import org.medhis2yes.form.model.FieldUiModel

interface EventCaptureFormView {

    fun showFields(fields: MutableList<FieldUiModel>)
    fun performSaveClick()
}
