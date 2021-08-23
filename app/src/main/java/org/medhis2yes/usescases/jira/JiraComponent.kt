package org.medhis2yes.usescases.jira

import dagger.Subcomponent
import org.medhis2yes.data.dagger.PerFragment

@PerFragment
@Subcomponent(modules = [JiraModule::class])
interface JiraComponent {
    fun inject(jiraFragment: JiraFragment)
}
