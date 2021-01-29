package org.dhis2hiv.utils

data class TestingCredential(
    val server_url: String,
    val user_name: String,
    val user_pass: String,
    val server_version: String? = null
)
