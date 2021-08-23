package org.medhis2yes.data.jira

data class ClickedIssueData(
    val uriString: String,
    val auth: String
) {
    fun authHeader() = "Authorization"
    fun basicAuth() = auth.toBasicAuth()
}
