package org.medhis2yes.usescases.settings

import org.medhis2yes.utils.Validator

class GatewayValidator : Validator {

    override fun validate(text: String): Boolean {
        return text.matches(regex)
    }

    companion object {
        val max_size = 16
        val regex = Regex("^\\+[1-9][0-9]{3,16}\$")
    }
}
