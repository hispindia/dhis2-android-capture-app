package org.dhis2afgamis.data.forms.dataentry.validation

import org.dhis2afgamis.utils.Validator

class IntegerValidator : Validator {

    override fun validate(text: String) = text.toIntOrNull()?.let { true } ?: false
}
