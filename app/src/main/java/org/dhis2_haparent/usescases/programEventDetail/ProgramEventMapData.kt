package org.dhis2_haparent.usescases.programEventDetail

import com.mapbox.geojson.BoundingBox
import com.mapbox.geojson.FeatureCollection
import org.dhis2_haparent.commons.data.ProgramEventViewModel

data class ProgramEventMapData(
    val events: List<ProgramEventViewModel>,
    val featureCollectionMap: MutableMap<String, FeatureCollection>,
    val boundingBox: BoundingBox
)
