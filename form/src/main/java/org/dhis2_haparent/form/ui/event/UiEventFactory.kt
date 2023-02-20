package org.dhis2_haparent.form.ui.event

import org.dhis2_haparent.form.model.FieldUiModel
import org.dhis2_haparent.form.model.UiEventType
import org.dhis2_haparent.form.model.UiRenderType

interface UiEventFactory {

    fun generateEvent(
        value: String?,
        uiEventType: UiEventType? = null,
        renderingType: UiRenderType? = null,
        fieldUiModel: FieldUiModel
    ): RecyclerViewUiEvents?
}
