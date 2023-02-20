package org.dhis2_haparent.usescases.teiDashboard.dashboardfragments.indicators

import android.content.Context
import org.dhis2_haparent.App
import org.dhis2_haparent.commons.Constants
import org.dhis2_haparent.usescases.eventsWithoutRegistration.eventCapture.EventCaptureActivity
import org.dhis2_haparent.usescases.teiDashboard.TeiDashboardMobileActivity

class IndicatorInjector(private val indicatorsFragment: IndicatorsFragment) {
    fun inject(context: Context) {
        indicatorsFragment.apply {
            arguments?.let {
                if (it.getString(VISUALIZATION_TYPE) == VisualizationType.TRACKER.name) {
                    injectIndicatorsForTracker(context)
                } else {
                    injectIndicatorsForEvents(context)
                }
            }
        }
    }

    private fun injectIndicatorsForTracker(context: Context) {
        val activity = context as TeiDashboardMobileActivity
        ((context.applicationContext) as App).dashboardComponent()!!
            .plus(
                IndicatorsModule(
                    activity.programUid,
                    activity.teiUid,
                    indicatorsFragment,
                    VisualizationType.TRACKER
                )
            )
            .inject(indicatorsFragment)
    }

    private fun injectIndicatorsForEvents(context: Context) {
        val activity = context as EventCaptureActivity
        activity.eventCaptureComponent.plus(
            IndicatorsModule(
                activity.intent.getStringExtra(Constants.PROGRAM_UID) ?: "",
                activity.intent.getStringExtra(Constants.EVENT_UID) ?: "",
                indicatorsFragment,
                VisualizationType.EVENTS
            )
        ).inject(indicatorsFragment)
    }
}
