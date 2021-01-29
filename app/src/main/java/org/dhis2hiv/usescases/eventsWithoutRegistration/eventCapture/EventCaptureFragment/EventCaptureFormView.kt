package org.dhis2hiv.usescases.eventsWithoutRegistration.eventCapture.EventCaptureFragment

import io.reactivex.processors.FlowableProcessor
import org.dhis2hiv.data.forms.dataentry.fields.FieldViewModel
import org.dhis2hiv.data.forms.dataentry.fields.RowAction

interface EventCaptureFormView {
    fun dataEntryFlowable(): FlowableProcessor<RowAction>
    fun sectionSelectorFlowable(): FlowableProcessor<String>
    fun showFields(
        fields: MutableList<FieldViewModel>,
        lastFocusItem: String
    )

    fun saveOpenedSection(it: String?)
}
