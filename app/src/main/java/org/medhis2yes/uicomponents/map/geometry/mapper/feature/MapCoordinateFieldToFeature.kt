package org.medhis2yes.uicomponents.map.geometry.mapper.feature

import com.mapbox.geojson.Feature
import org.medhis2yes.data.dhislogic.CoordinateAttributeInfo
import org.medhis2yes.data.dhislogic.CoordinateDataElementInfo
import org.medhis2yes.uicomponents.map.extensions.FeatureSource
import org.medhis2yes.uicomponents.map.extensions.PROPERTY_FEATURE_SOURCE
import org.medhis2yes.uicomponents.map.geometry.mapper.MapGeometryToFeature
import org.medhis2yes.uicomponents.map.geometry.mapper.featurecollection.MapCoordinateFieldToFeatureCollection.Companion.EVENT
import org.medhis2yes.uicomponents.map.geometry.mapper.featurecollection.MapCoordinateFieldToFeatureCollection.Companion.FIELD_NAME
import org.medhis2yes.uicomponents.map.geometry.mapper.featurecollection.MapCoordinateFieldToFeatureCollection.Companion.STAGE
import org.medhis2yes.uicomponents.map.geometry.mapper.featurecollection.MapCoordinateFieldToFeatureCollection.Companion.TEI

class MapCoordinateFieldToFeature(private val mapGeometryToFeature: MapGeometryToFeature) {

    fun map(coordinateDataElementInfo: CoordinateDataElementInfo): Feature? {
        return mapGeometryToFeature.map(
            coordinateDataElementInfo.geometry,
            hashMapOf(
                PROPERTY_FEATURE_SOURCE to FeatureSource.FIELD.name,
                FIELD_NAME to coordinateDataElementInfo.dataElement.displayFormName()!!,
                EVENT to coordinateDataElementInfo.event.uid()!!,
                STAGE to coordinateDataElementInfo.stage.displayName()!!
            ).apply {
                coordinateDataElementInfo.enrollment?.let { enrollment ->
                    put(
                        TEI,
                        enrollment.trackedEntityInstance()!!
                    )
                }
            }
        )
    }

    fun map(coordinateAttributeInfo: CoordinateAttributeInfo): Feature? {
        return mapGeometryToFeature.map(
            coordinateAttributeInfo.geometry,
            hashMapOf(
                PROPERTY_FEATURE_SOURCE to FeatureSource.FIELD.name,
                FIELD_NAME to coordinateAttributeInfo.attribute.displayFormName()!!,
                TEI to coordinateAttributeInfo.tei.uid()!!
            )
        )
    }
}
