package org.dhis2_haparent.data.dispatcher

import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import org.dhis2_haparent.form.model.DispatcherProvider
import org.dhis2_haparent.form.model.coroutine.FormDispatcher

@Module
open class DispatcherModule {

    @Provides
    @Singleton
    open fun provideDispatcherModule(): DispatcherProvider {
        return FormDispatcher()
    }
}
