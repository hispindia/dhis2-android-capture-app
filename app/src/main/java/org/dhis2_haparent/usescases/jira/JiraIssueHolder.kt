package org.dhis2_haparent.usescases.jira

import androidx.recyclerview.widget.RecyclerView
import org.dhis2_haparent.data.jira.JiraIssue
import org.dhis2_haparent.databinding.JiraIssueItemBinding

class JiraIssueHolder(
    private val binding: JiraIssueItemBinding,
    private val onJiraIssueClick: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(jiraIssue: JiraIssue) {
        binding.apply {
            issue = jiraIssue
            jiraCard.setOnClickListener { onJiraIssueClick(jiraIssue.key) }
        }
    }
}
