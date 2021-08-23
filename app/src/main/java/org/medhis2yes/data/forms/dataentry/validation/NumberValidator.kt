package org.medhis2yes.data.forms.dataentry.validation

import org.medhis2yes.utils.Validator

class NumberValidator : Validator {

    override fun validate(text: String) = text.toDoubleOrNull()?.let { true } ?: false
}
