package org.dhis2_haparent.data.jira

data class JiraIssueListResponse(
    val maxResults: Int,
    val total: Int,
    val issues: List<JiraIssue>
)
