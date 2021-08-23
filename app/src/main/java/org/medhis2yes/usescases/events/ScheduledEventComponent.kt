package org.medhis2yes.usescases.events

import dagger.Subcomponent
import org.medhis2yes.data.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [ScheduledEventModule::class])
interface ScheduledEventComponent {
    fun inject(scheduledEventActivity: ScheduledEventActivity)
}
