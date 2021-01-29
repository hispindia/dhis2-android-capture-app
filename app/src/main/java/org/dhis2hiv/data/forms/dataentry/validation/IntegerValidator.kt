package org.dhis2hiv.data.forms.dataentry.validation

import org.dhis2hiv.utils.Validator

class IntegerValidator : Validator {

    override fun validate(text: String) = text.toIntOrNull()?.let { true } ?: false
}
