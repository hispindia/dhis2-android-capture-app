package org.fpandhis2.data.schedulers

import io.reactivex.schedulers.Schedulers

class TrampolineSchedulerProvider : SchedulerProvider {
    override fun computation() = Schedulers.trampoline()
    override fun io() = Schedulers.trampoline()
    override fun ui() = Schedulers.trampoline()
}
