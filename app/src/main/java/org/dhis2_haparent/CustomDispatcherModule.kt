package org.dhis2_haparent

import dagger.Module
import dagger.Provides
import dispatch.core.DispatcherProvider
import javax.inject.Singleton

@Module
class CustomDispatcherModule {
    @Provides
    @Singleton
    fun provideCustomDispatcherProvider(): DispatcherProvider {
        return DispatcherProvider()
    }
}
