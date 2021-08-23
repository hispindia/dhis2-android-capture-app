package org.medhis2yes.usescases.jira

import androidx.recyclerview.widget.RecyclerView
import org.medhis2yes.data.jira.JiraIssue
import org.medhis2yes.databinding.JiraIssueItemBinding

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
