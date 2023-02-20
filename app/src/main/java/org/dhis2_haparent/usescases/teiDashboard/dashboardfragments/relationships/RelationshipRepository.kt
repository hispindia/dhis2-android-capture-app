package org.dhis2_haparent.usescases.teiDashboard.dashboardfragments.relationships

import io.reactivex.Single
import org.dhis2_haparent.commons.data.RelationshipViewModel
import org.hisp.dhis.android.core.relationship.RelationshipType

interface RelationshipRepository {
    fun relationshipTypes(): Single<List<Pair<RelationshipType, String>>>
    fun relationships(): Single<List<RelationshipViewModel>>
    fun getTeiTypeDefaultRes(teiTypeUid: String): Int
    fun getEventProgram(eventUid: String): String
}
