package org.dhis2_haparent.utils.granularsync

sealed class ConvertTaskResult {
    data class Message(val smsMessage: String) : ConvertTaskResult()
    data class Count(val smsCount: Int) : ConvertTaskResult()
}
