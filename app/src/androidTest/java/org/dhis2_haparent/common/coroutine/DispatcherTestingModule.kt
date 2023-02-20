package org.dhis2_haparent.common.coroutine

import dagger.Module
import dagger.Provides
import org.dhis2_haparent.data.dispatcher.DispatcherModule
import org.dhis2_haparent.form.model.DispatcherProvider
import org.dhis2_haparent.form.model.coroutine.EspressoTestingDispatcher
import javax.inject.Singleton

@Module
class DispatcherTestingModule: DispatcherModule() {

    @Provides
    @Singleton
    override fun provideDispatcherModule(): DispatcherProvider {
        return EspressoTestingDispatcher()
    }
}