package org.medhis2yes.uicomponents.map.geometry.mapper.featurecollection

import com.mapbox.geojson.FeatureCollection
import org.medhis2yes.data.dhislogic.CoordinateAttributeInfo
import org.medhis2yes.data.dhislogic.CoordinateDataElementInfo
import org.medhis2yes.data.dhislogic.CoordinateFieldInfo

class MapCoordinateFieldToFeatureCollection(
    private val mapDataElementToFeature: MapDataElementToFeature,
    private val mapAttributeToFeature: MapAttributeToFeature

) {

    fun map(coordinateFieldInfos: List<CoordinateFieldInfo>): Map<String, FeatureCollection> {
        return when {
            coordinateFieldInfos.any { it is CoordinateDataElementInfo } -> {
                mapDataElementToFeature.mapDataElement(
                    coordinateFieldInfos as List<CoordinateDataElementInfo>
                )
            }
            coordinateFieldInfos.any { it is CoordinateAttributeInfo } -> {
                mapAttributeToFeature.mapAttribute(
                    coordinateFieldInfos as List<CoordinateAttributeInfo>
                )
            }
            else -> {
                emptyMap()
            }
        }
    }

    companion object {
        const val EVENT = "eventUid"
        const val STAGE = "stageUid"
        const val TEI = "teiUid"
        const val FIELD_NAME = "fieldName"
    }
}
