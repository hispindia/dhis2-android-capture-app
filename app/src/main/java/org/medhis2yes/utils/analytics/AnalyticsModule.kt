package org.medhis2yes.utils.analytics

import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import org.medhis2yes.utils.analytics.matomo.MatomoAnalyticsController

@Module
@Singleton
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
