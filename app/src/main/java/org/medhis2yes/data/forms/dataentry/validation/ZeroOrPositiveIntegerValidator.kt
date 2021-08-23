package org.medhis2yes.data.forms.dataentry.validation

import org.medhis2yes.utils.Validator

class ZeroOrPositiveIntegerValidator : Validator {

    override fun validate(text: String) = text.toIntOrNull()?.let {
        text.matches(regex)
    } ?: false

    companion object {
        val regex = Regex("^[0-9]*\$")
    }
}
