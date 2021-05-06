package org.fpandhis2.usescases.events

import dagger.Subcomponent
import org.fpandhis2.data.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [ScheduledEventModule::class])
interface ScheduledEventComponent {
    fun inject(scheduledEventActivity: ScheduledEventActivity)
}
