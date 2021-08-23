package org.medhis2yes.data.forms.dataentry.validation

import org.medhis2yes.utils.Validator

class IntegerValidator : Validator {

    override fun validate(text: String) = text.toIntOrNull()?.let { true } ?: false
}
