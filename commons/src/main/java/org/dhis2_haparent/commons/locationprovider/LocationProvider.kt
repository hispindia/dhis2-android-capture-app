package org.dhis2_haparent.commons.locationprovider

import android.location.Location

interface LocationProvider {

    fun getLastKnownLocation(
        onNewLocation: (Location) -> Unit,
        onPermissionNeeded: () -> Unit,
        onLocationDisabled: () -> Unit
    )

    fun stopLocationUpdates()

    fun hasLocationEnabled(): Boolean
}
