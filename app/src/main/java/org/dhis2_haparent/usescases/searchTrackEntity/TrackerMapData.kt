package org.dhis2_haparent.usescases.searchTrackEntity

import com.mapbox.geojson.BoundingBox
import com.mapbox.geojson.FeatureCollection
import java.util.HashMap
import org.dhis2_haparent.commons.data.CarouselItemModel
import org.dhis2_haparent.commons.data.SearchTeiModel
import org.dhis2_haparent.maps.mapper.MapRelationshipToRelationshipMapModel

data class TrackerMapData(
    val teiModels: MutableList<SearchTeiModel>,
    val eventFeatures: org.dhis2_haparent.maps.geometry.mapper.EventsByProgramStage,
    val teiFeatures: HashMap<String, FeatureCollection>,
    val teiBoundingBox: BoundingBox,
    val eventModels: MutableList<org.dhis2_haparent.maps.model.EventUiComponentModel>,
    val dataElementFeaturess: MutableMap<String, FeatureCollection>
) {
    fun allItems() = mutableListOf<CarouselItemModel>().apply {
        addAll(teiModels)
        addAll(eventModels)
        teiModels.forEach {
            addAll(
                MapRelationshipToRelationshipMapModel().mapList(it.relationships)
            )
        }
    }
}
