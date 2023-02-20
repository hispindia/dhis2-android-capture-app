package org.dhis2_haparent.commons.schedulers

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SchedulersModule {
    @Provides
    @Singleton
    fun schedulerProvider(): SchedulerProvider {
        return SchedulersProviderImpl()
    }
}
