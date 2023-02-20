package org.dhis2_haparent.utils.analytics

import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import org.dhis2_haparent.commons.matomo.MatomoAnalyticsController

@Module
class AnalyticsModule internal constructor() {

    @Provides
    @Singleton
    fun providesAnalyticsHelper(
        matomoAnalyticsController: MatomoAnalyticsController
    ): AnalyticsHelper {
        return AnalyticsHelper(matomoAnalyticsController)
    }

    @Provides
    fun providesAnalyticsInterceptor(analyticHelper: AnalyticsHelper): AnalyticsInterceptor {
        return AnalyticsInterceptor(analyticHelper)
    }
}
