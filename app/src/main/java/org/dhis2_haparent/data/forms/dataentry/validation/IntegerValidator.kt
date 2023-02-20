package org.dhis2_haparent.data.forms.dataentry.validation

import org.dhis2_haparent.utils.Validator

class IntegerValidator : Validator {

    override fun validate(text: String) = text.toIntOrNull()?.let { true } ?: false
}
