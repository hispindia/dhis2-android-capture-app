package org.dhis2hiv.usescases.settings.models

data class SMSSettingsViewModel(
    val isEnabled: Boolean,
    val gatewayNumber: String,
    val responseNumber: String,
    val responseTimeout: Int,
    val isGatewayNumberEditable: Boolean,
    val isResponseNumberEditable: Boolean,
    val waitingForResponse: Boolean
)
