package org.medhis2yes.usescases.main

import dagger.Subcomponent
import org.medhis2yes.data.dagger.PerActivity
import org.medhis2yes.usescases.jira.JiraComponent
import org.medhis2yes.usescases.jira.JiraModule

@PerActivity
@Subcomponent(modules = [MainModule::class])
interface MainComponent {
    fun inject(mainActivity: MainActivity)
    fun plus(jiraModule: JiraModule): JiraComponent
}
