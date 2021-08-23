package org.medhis2yes.utils.reporting

interface CrashReportController {

    fun trackScreenName(screen: String)

    fun trackUser(user: String?, server: String?)

    fun trackServer(server: String?)

    fun logException(exception: Exception)

    fun logMessage(message: String)
}
