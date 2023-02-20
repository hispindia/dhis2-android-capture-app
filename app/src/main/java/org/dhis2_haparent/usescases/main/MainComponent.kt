package org.dhis2_haparent.usescases.main

import dagger.Subcomponent
import org.dhis2_haparent.commons.di.dagger.PerActivity
import org.dhis2_haparent.usescases.jira.JiraComponent
import org.dhis2_haparent.usescases.jira.JiraModule
import org.dhis2_haparent.usescases.troubleshooting.TroubleshootingComponent
import org.dhis2_haparent.usescases.troubleshooting.TroubleshootingModule

@PerActivity
@Subcomponent(modules = [MainModule::class])
interface MainComponent {
    fun inject(mainActivity: MainActivity)
    fun plus(jiraModule: JiraModule): JiraComponent
    fun plus(troubleShootingModule: TroubleshootingModule): TroubleshootingComponent
}
