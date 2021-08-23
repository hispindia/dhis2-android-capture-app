package org.medhis2yes.usescases.jira

import dagger.Module
import dagger.Provides
import org.medhis2yes.data.dagger.PerFragment
import org.medhis2yes.data.prefs.PreferenceProvider
import org.medhis2yes.data.schedulers.SchedulerProvider
import org.medhis2yes.utils.resources.ResourceManager

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
