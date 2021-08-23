package org.medhis2yes.usescases.teiDashboard.dashboardfragments.indicators

import dagger.Module
import dagger.Provides
import dhis2.org.analytics.charts.Charts
import org.medhis2yes.data.dagger.PerFragment
import org.medhis2yes.data.forms.dataentry.RuleEngineRepository
import org.medhis2yes.data.schedulers.SchedulerProvider
import org.medhis2yes.utils.resources.ResourceManager
import org.hisp.dhis.android.core.D2

@PerFragment
@Module
class IndicatorsModule(
    val programUid: String,
    val recordUid: String,
    val view: IndicatorsView,
    private val visualizationType: VisualizationType
) {

    @Provides
    @PerFragment
    fun providesPresenter(
        schedulerProvider: SchedulerProvider,
        indicatorRepository: IndicatorRepository
    ): IndicatorsPresenter {
        return IndicatorsPresenter(schedulerProvider, view, indicatorRepository)
    }

    @Provides
    @PerFragment
    fun provideRepository(
        d2: D2,
        ruleEngineRepository: RuleEngineRepository,
        charts: Charts?,
        resourceManager: ResourceManager
    ): IndicatorRepository {
        return if (visualizationType == VisualizationType.TRACKER) {
            TrackerAnalyticsRepository(
                d2,
                ruleEngineRepository,
                charts,
                programUid,
                recordUid,
                resourceManager
            )
        } else {
            EventIndicatorRepository(
                d2,
                ruleEngineRepository,
                programUid,
                recordUid,
                resourceManager
            )
        }
    }
}
