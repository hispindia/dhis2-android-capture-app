package org.dhis2_haparent.usescases.teiDashboard.dashboardfragments.relationships

import androidx.lifecycle.LiveData

interface MapButtonObservable {
    fun relationshipMap(): LiveData<Boolean>
    fun onRelationshipMapLoaded()
}
