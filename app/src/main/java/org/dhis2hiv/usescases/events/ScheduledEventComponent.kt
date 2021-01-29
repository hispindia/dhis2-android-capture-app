package org.dhis2hiv.usescases.events

import dagger.Subcomponent
import org.dhis2hiv.data.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [ScheduledEventModule::class])
interface ScheduledEventComponent {
    fun inject(scheduledEventActivity: ScheduledEventActivity)
}
