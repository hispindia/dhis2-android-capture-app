package org.dhis2_haparent.usescases.jira

import dagger.Subcomponent
import org.dhis2_haparent.commons.di.dagger.PerFragment

@PerFragment
@Subcomponent(modules = [JiraModule::class])
interface JiraComponent {
    fun inject(jiraFragment: JiraFragment)
}
