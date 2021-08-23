package org.medhis2yes.usescases.teiDashboard.dashboardfragments.indicators

import org.medhis2yes.data.analytics.AnalyticsModel
import org.medhis2yes.usescases.general.AbstractActivityContracts

interface IndicatorsView : AbstractActivityContracts.View {
    fun swapAnalytics(analytics: List<AnalyticsModel>)
}
