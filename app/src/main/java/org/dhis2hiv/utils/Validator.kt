package org.dhis2hiv.utils

interface Validator {
    fun validate(text: String): Boolean
}
