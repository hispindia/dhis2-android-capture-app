package org.dhis2_haparent.commons.dialogs.bottomsheet

import org.dhis2_haparent.commons.data.FieldWithIssue

data class BottomSheetDialogUiModel(
    var title: String,
    var subtitle: String,
    var iconResource: Int,
    var fieldsWithIssues: List<FieldWithIssue> = emptyList(),
    var mainButton: DialogButtonStyle,
    var secondaryButton: DialogButtonStyle? = null
)
