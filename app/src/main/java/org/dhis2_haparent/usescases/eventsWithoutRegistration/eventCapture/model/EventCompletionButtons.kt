package org.dhis2_haparent.usescases.eventsWithoutRegistration.eventCapture.model

import org.dhis2_haparent.commons.dialogs.bottomsheet.DialogButtonStyle
import org.dhis2_haparent.utils.customviews.FormBottomDialog

data class EventCompletionButtons(
    val buttonStyle: DialogButtonStyle,
    val action: FormBottomDialog.ActionType
)
