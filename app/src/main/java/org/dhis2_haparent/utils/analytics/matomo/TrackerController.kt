package org.dhis2_haparent.utils.analytics.matomo

import org.dhis2_haparent.BuildConfig
import org.matomo.sdk.Matomo
import org.matomo.sdk.Tracker
import org.matomo.sdk.TrackerBuilder

const val DEFAULT_EXTERNAL_TRACKER_NAME = "secondaryTracker"

class TrackerController {
    companion object {
        fun dhis2InternalTracker(matomo: Matomo): Tracker? {
            return TrackerBuilder.createDefault(BuildConfig.MATOMO_URL, BuildConfig.MATOMO_ID)
                .build(matomo)
        }

        fun dhis2ExternalTracker(
            matomo: Matomo,
            matomoUrl: String,
            siteId: Int,
            trackerName: String
        ): Tracker? {
            return when (BuildConfig.DEBUG) {
                true -> null
                false ->
                    TrackerBuilder(matomoUrl, siteId, trackerName)
                        .build(matomo)
            }
        }
    }
}
