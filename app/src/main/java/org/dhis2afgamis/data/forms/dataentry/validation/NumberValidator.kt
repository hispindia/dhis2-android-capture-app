package org.dhis2afgamis.data.forms.dataentry.validation

import org.dhis2afgamis.utils.Validator

class NumberValidator : Validator {

    override fun validate(text: String) = text.toDoubleOrNull()?.let { true } ?: false
}
