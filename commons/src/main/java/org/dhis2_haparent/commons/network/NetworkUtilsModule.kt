package org.dhis2_haparent.commons.network

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class NetworkUtilsModule {

    @Provides
    @Singleton
    open fun networkUtilsProvider(context: Context): NetworkUtils {
        return NetworkUtils(context)
    }
}
