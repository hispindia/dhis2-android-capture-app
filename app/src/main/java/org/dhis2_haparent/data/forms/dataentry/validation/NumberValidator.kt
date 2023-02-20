package org.dhis2_haparent.data.forms.dataentry.validation

import org.dhis2_haparent.utils.Validator

class NumberValidator : Validator {

    override fun validate(text: String) = text.toDoubleOrNull()?.let { true } ?: false
}
