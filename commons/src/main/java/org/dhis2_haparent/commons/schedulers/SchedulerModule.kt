package org.dhis2_haparent.commons.schedulers

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SchedulerModule(private val schedulerProvider: SchedulerProvider) {
    @Provides
    @Singleton
    fun schedulerProvider(): SchedulerProvider {
        return schedulerProvider
    }
}
