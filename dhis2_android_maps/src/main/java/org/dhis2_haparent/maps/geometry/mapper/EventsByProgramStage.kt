package org.dhis2_haparent.maps.geometry.mapper

import com.mapbox.geojson.FeatureCollection

data class EventsByProgramStage(
    val tag: String,
    val featureCollectionMap: Map<String, FeatureCollection>
)
