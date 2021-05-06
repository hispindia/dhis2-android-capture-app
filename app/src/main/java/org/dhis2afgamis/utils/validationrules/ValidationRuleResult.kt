package org.dhis2afgamis.utils.validationrules

import org.hisp.dhis.android.core.validation.engine.ValidationResult

data class ValidationRuleResult(
    val validationResultStatus: ValidationResult.ValidationResultStatus,
    val violations: List<Violation>
)

data class Violation(
    val description: String?,
    val instruction: String?,
    val dataToReview: List<DataToReview>
)

data class DataToReview(
    val dataElementUid: String,
    val dataElementDisplayName: String?,
    val categoryOptionComboUid: String,
    val categoryOptionComboDisplayName: String?,
    val value: String
)
