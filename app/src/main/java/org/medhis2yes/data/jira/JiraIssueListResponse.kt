package org.medhis2yes.data.jira

data class JiraIssueListResponse(
    val maxResults: Int,
    val total: Int,
    val issues: List<JiraIssue>
)
