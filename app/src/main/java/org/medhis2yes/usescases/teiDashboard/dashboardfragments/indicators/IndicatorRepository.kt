package org.medhis2yes.usescases.teiDashboard.dashboardfragments.indicators

import io.reactivex.Flowable
import org.medhis2yes.data.analytics.AnalyticsModel

interface IndicatorRepository {
    fun fetchData(): Flowable<List<AnalyticsModel>>
}
