package org.dhis2_haparent.data.jira

data class ClickedIssueData(
    val uriString: String,
    val auth: String
) {
    fun authHeader() = "Authorization"
    fun basicAuth() = auth.toBasicAuth()
}
