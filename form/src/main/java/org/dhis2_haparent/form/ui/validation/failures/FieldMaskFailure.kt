package org.dhis2_haparent.form.ui.validation.failures

sealed class FieldMaskFailure : Throwable() {
    object WrongPatternException : FieldMaskFailure()
    object InvalidPatternException : FieldMaskFailure()
}
