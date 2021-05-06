package org.fpandhis2.data.forms.dataentry.validation

import org.fpandhis2.utils.Validator

class PositiveIntegerValidator : Validator {

    override fun validate(text: String) = text.toIntOrNull()?.let {
        text.matches(regex)
    } ?: false

    companion object {
        val regex = Regex("^[0-9]*[1-9][0-9]*\$")
    }
}
