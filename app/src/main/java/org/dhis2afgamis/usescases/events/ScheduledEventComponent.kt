package org.dhis2afgamis.usescases.events

import dagger.Subcomponent
import org.dhis2afgamis.data.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [ScheduledEventModule::class])
interface ScheduledEventComponent {
    fun inject(scheduledEventActivity: ScheduledEventActivity)
}
