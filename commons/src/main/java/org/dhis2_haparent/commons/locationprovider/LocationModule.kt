package org.dhis2_haparent.commons.locationprovider

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocationModule {
    @Provides
    @Singleton
    fun locationProvider(context: Context): LocationProvider {
        return LocationProviderImpl(context)
    }
}
