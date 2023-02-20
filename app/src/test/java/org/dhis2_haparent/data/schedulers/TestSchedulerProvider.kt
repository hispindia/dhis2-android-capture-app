package org.dhis2_haparent.data.schedulers

import io.reactivex.schedulers.TestScheduler
import org.dhis2_haparent.commons.schedulers.SchedulerProvider

class TestSchedulerProvider(private val scheduler: TestScheduler) : SchedulerProvider {
    override fun computation() = scheduler
    override fun ui() = scheduler
    override fun io() = scheduler
}
