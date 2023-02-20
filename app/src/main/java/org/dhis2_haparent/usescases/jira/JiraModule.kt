package org.dhis2_haparent.usescases.jira

import dagger.Module
import dagger.Provides
import org.dhis2_haparent.commons.di.dagger.PerFragment
import org.dhis2_haparent.commons.prefs.PreferenceProvider
import org.dhis2_haparent.commons.resources.ResourceManager
import org.dhis2_haparent.commons.schedulers.SchedulerProvider

@Module
class JiraModule {
    @Provides
    @PerFragment
    fun jiraViewModelFactory(
        preferenceProvider: PreferenceProvider,
        resourceManager: ResourceManager,
        schedulerProvider: SchedulerProvider
    ): JiraViewModelFactory {
        return JiraViewModelFactory(preferenceProvider, resourceManager, schedulerProvider)
    }
}
