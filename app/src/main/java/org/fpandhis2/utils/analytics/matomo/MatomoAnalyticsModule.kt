package org.fpandhis2.utils.analytics.matomo

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import org.fpandhis2.App

@Module
class MatomoAnalyticsModule {

    @Provides
    @Singleton
    fun providesMatomoAnalyticsController(
        context: Context
    ): MatomoAnalyticsController {
        val tracker = (context.applicationContext as App).tracker
        return MatomoAnalyticsControllerImpl(tracker)
    }
}
