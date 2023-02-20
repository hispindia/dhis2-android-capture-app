package org.dhis2_haparent.commons.reporting

interface CrashReportController {

    fun trackUser(user: String?, server: String?)

    fun trackServer(server: String?)

    fun trackError(exception: Exception, message: String?)

    fun logMessage(message: String)

    fun addBreadCrumb(category: String, message: String)
}
