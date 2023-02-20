package org.dhis2_haparent.usescases.events

import dagger.Subcomponent
import org.dhis2_haparent.commons.di.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [ScheduledEventModule::class])
interface ScheduledEventComponent {
    fun inject(scheduledEventActivity: ScheduledEventActivity)
}
