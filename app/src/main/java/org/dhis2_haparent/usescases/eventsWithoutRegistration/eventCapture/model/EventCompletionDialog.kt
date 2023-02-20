package org.dhis2_haparent.usescases.eventsWithoutRegistration.eventCapture.model

import org.dhis2_haparent.commons.dialogs.bottomsheet.BottomSheetDialogUiModel
import org.dhis2_haparent.utils.customviews.FormBottomDialog

data class EventCompletionDialog(
    val bottomSheetDialogUiModel: BottomSheetDialogUiModel,
    val mainButtonAction: FormBottomDialog.ActionType,
    val secondaryButtonAction: FormBottomDialog.ActionType
)
