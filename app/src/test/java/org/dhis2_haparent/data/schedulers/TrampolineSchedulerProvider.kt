package org.dhis2_haparent.data.schedulers

import io.reactivex.schedulers.Schedulers
import org.dhis2_haparent.commons.schedulers.SchedulerProvider

class TrampolineSchedulerProvider : SchedulerProvider {
    override fun computation() = Schedulers.trampoline()
    override fun io() = Schedulers.trampoline()
    override fun ui() = Schedulers.trampoline()
}
