package org.fpandhis2.usescases.eventsWithoutRegistration.eventCapture.EventCaptureFragment

import io.reactivex.processors.FlowableProcessor
import org.fpandhis2.data.forms.dataentry.fields.FieldViewModel
import org.fpandhis2.data.forms.dataentry.fields.RowAction

interface EventCaptureFormView {
    fun dataEntryFlowable(): FlowableProcessor<RowAction>
    fun sectionSelectorFlowable(): FlowableProcessor<String>
    fun showFields(
        fields: MutableList<FieldViewModel>,
        lastFocusItem: String
    )

    fun saveOpenedSection(it: String?)
}
