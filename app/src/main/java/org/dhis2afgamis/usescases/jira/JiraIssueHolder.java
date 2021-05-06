package org.dhis2afgamis.usescases.jira;

import org.dhis2afgamis.databinding.JiraIssueItemBinding;
import org.dhis2afgamis.utils.jira.JiraIssue;
import org.dhis2afgamis.utils.jira.OnJiraIssueClick;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class JiraIssueHolder extends RecyclerView.ViewHolder {
    private final JiraIssueItemBinding binding;

    JiraIssueHolder(@NonNull JiraIssueItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(JiraIssue jiraIssue, OnJiraIssueClick listener) {
        binding.setIssue(jiraIssue);
        itemView.setOnClickListener(view -> listener.onJiraIssueClick(jiraIssue.getKey()));
    }
}
