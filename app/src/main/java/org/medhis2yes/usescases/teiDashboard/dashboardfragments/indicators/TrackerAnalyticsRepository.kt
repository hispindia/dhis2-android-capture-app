package org.medhis2yes.usescases.teiDashboard.dashboardfragments.indicators

import dhis2.org.analytics.charts.Charts
import io.reactivex.Flowable
import io.reactivex.functions.Function3
import org.medhis2yes.data.analytics.AnalyticsModel
import org.medhis2yes.data.analytics.ChartModel
import org.medhis2yes.data.forms.dataentry.RuleEngineRepository
import org.medhis2yes.utils.DhisTextUtils
import org.medhis2yes.utils.resources.ResourceManager
import org.hisp.dhis.android.core.D2

class TrackerAnalyticsRepository(
    d2: D2,
    ruleEngineRepository: RuleEngineRepository,
    val charts: Charts?,
    programUid: String,
    val teiUid: String,
    resourceManager: ResourceManager
) : BaseIndicatorRepository(d2, ruleEngineRepository, programUid, resourceManager) {

    val enrollmentUid: String

    init {
        var enrollmentRepository = d2.enrollmentModule().enrollments()
            .byTrackedEntityInstance().eq(teiUid)
        if (!DhisTextUtils.isEmpty(programUid)) {
            enrollmentRepository = enrollmentRepository.byProgram().eq(programUid)
        }

        enrollmentUid = if (enrollmentRepository.one().blockingGet() == null) {
            ""
        } else {
            enrollmentRepository.one().blockingGet().uid()
        }
    }

    override fun fetchData(): Flowable<List<AnalyticsModel>> {
        return Flowable.zip<List<AnalyticsModel>?,
            List<AnalyticsModel>?,
            List<AnalyticsModel>,
            List<AnalyticsModel>>(
            getIndicators(
                !DhisTextUtils.isEmpty(enrollmentUid)
            ) { indicatorUid ->
                d2.programModule()
                    .programIndicatorEngine().getEnrollmentProgramIndicatorValue(
                        enrollmentUid,
                        indicatorUid
                    )
            },
            getRulesIndicators(),
            Flowable.just(
                charts?.getCharts(enrollmentUid)?.map { ChartModel(it) }
            ),
            Function3 { indicators, ruleIndicators, charts ->
                arrangeSections(indicators, ruleIndicators, charts)
            }
        )
    }
}
