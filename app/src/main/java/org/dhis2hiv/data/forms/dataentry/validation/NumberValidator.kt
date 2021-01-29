package org.dhis2hiv.data.forms.dataentry.validation

import org.dhis2hiv.utils.Validator

class NumberValidator : Validator {

    override fun validate(text: String) = text.toDoubleOrNull()?.let { true } ?: false
}
