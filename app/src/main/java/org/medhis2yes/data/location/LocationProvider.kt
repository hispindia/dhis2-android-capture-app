package org.medhis2yes.data.location

import android.location.Location

interface LocationProvider {
    fun requestLocationUpdates(onNewLocation: (Location) -> Unit)
    fun getLastKnownLocation(
        onNewLocation: (Location) -> Unit,
        onPermissionNeeded: () -> Unit,
        onLocationDisabled: () -> Unit
    )
}
