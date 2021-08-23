package org.medhis2yes.usescases.teiDashboard.dashboardfragments.indicators

import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import org.medhis2yes.data.analytics.AnalyticsModel
import org.medhis2yes.data.forms.dataentry.RuleEngineRepository
import org.medhis2yes.utils.resources.ResourceManager
import org.hisp.dhis.android.core.D2

class EventIndicatorRepository(
    d2: D2,
    ruleEngineRepository: RuleEngineRepository,
    programUid: String,
    val eventUid: String,
    resourceManager: ResourceManager
) : BaseIndicatorRepository(d2, ruleEngineRepository, programUid, resourceManager) {

    override fun fetchData(): Flowable<List<AnalyticsModel>> {
        return Flowable.zip<List<AnalyticsModel>?,
            List<AnalyticsModel>?,
            List<AnalyticsModel>>(
            getIndicators { indicatorUid ->
                d2.programModule()
                    .programIndicatorEngine().getEventProgramIndicatorValue(
                        eventUid,
                        indicatorUid
                    )
            },
            getRulesIndicators(),
            BiFunction { indicators, ruleIndicators ->
                arrangeSections(indicators, ruleIndicators)
            }
        )
    }
}
