package org.dhis2hiv.usescases.settings

import org.dhis2hiv.utils.Validator

class GatewayValidator : Validator {

    override fun validate(text: String): Boolean {
        return text.matches(regex)
    }

    companion object {
        val max_size = 16
        val regex = Regex("^\\+[1-9][0-9]{3,16}\$")
    }
}
