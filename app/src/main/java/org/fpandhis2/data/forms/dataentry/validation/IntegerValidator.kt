package org.fpandhis2.data.forms.dataentry.validation

import org.fpandhis2.utils.Validator

class IntegerValidator : Validator {

    override fun validate(text: String) = text.toIntOrNull()?.let { true } ?: false
}
