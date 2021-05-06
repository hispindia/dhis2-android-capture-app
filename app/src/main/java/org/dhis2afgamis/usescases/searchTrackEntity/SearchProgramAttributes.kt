package org.dhis2afgamis.usescases.searchTrackEntity

import org.hisp.dhis.android.core.common.ValueTypeDeviceRendering
import org.hisp.dhis.android.core.trackedentity.TrackedEntityAttribute

data class SearchProgramAttributes(
    val trackedEntityAttributes: List<TrackedEntityAttribute>,
    val rendering: List<ValueTypeDeviceRendering>
)
