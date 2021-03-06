package org.dhis2.uicomponents.map.layer.types

import android.graphics.Color
import com.mapbox.geojson.Feature
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.expressions.Expression
import com.mapbox.mapboxsdk.style.layers.FillLayer
import com.mapbox.mapboxsdk.style.layers.Layer
import com.mapbox.mapboxsdk.style.layers.LineLayer
import com.mapbox.mapboxsdk.style.layers.Property
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import org.dhis2.uicomponents.map.geometry.mapper.featurecollection.MapTeisToFeatureCollection.Companion.TEI_UID
import org.dhis2.uicomponents.map.layer.MapLayer
import org.dhis2.uicomponents.map.managers.TeiMapManager.Companion.TEIS_SOURCE_ID
import org.dhis2.utils.ColorUtils
import org.hisp.dhis.android.core.common.FeatureType

class TeiMapLayer(
    var style: Style,
    var featureType: FeatureType,
    private val enrollmentColor: Int,
    private val enrollmentDarkColor: Int
) : MapLayer {

    private var POINT_LAYER_ID: String = "TEI_POINT_LAYER_ID"
    private var SELECTED_POINT_LAYER_ID: String = "SELECTED_TEI_POINT_LAYER_ID"

    private var POLYGON_LAYER_ID: String = "TEI_POLYGON_LAYER_ID"
    private var POLYGON_BORDER_LAYER_ID: String = "TEI_POLYGON_BORDER_LAYER_ID"
    private var SELECTED_POLYGON_LAYER_ID: String = "SELECTED_POLYGON_LAYER_ID"
    private var SELECTED_POLYGON_BORDER_LAYER_ID: String = "SELECTED_POLYGON_BORDER_LAYER_ID"

    private var SELECTED_POINT_SOURCE_ID = "SELECTED_POINT_SOURCE"
    private var SELECTED_POLYGON_SOURCE_ID = "SELECTED_POLYGON_SOURCE_ID"

    override var visible = false

    init {
        when (featureType) {
            FeatureType.POINT -> {
                style.addLayer(pointLayer)
                style.addSource(GeoJsonSource(SELECTED_POINT_SOURCE_ID))
                style.addLayer(selectedPointLayer)
            }
            FeatureType.POLYGON -> {
                style.addLayer(polygonLayer)
                style.addLayer(polygonBorderLayer)
                style.addSource(GeoJsonSource(SELECTED_POLYGON_SOURCE_ID))
                style.addLayer(selectedPolygonLayer)
                style.addLayer(selectedPolygonBorderLayer)
            }
            else -> Unit
        }
    }

    private val pointLayer: Layer
        get() = style.getLayer(POINT_LAYER_ID)
            ?: SymbolLayer(POINT_LAYER_ID, TEIS_SOURCE_ID)
                .withProperties(
                    PropertyFactory.iconImage(Expression.get(TEI_UID)),
                    PropertyFactory.iconOffset(arrayOf(0f, -12f)),
                    PropertyFactory.iconAllowOverlap(true),
                    PropertyFactory.textAllowOverlap(true)
                ).withFilter(
                    Expression.eq(
                        Expression.literal("\$type"),
                        Expression.literal("Point")
                    )
                )

    private val selectedPointLayer: Layer
        get() = style.getLayer(SELECTED_POINT_LAYER_ID)
            ?: SymbolLayer(SELECTED_POINT_LAYER_ID, SELECTED_POINT_SOURCE_ID)
                .withProperties(
                    PropertyFactory.iconImage(Expression.get(TEI_UID)),
                    PropertyFactory.iconOffset(arrayOf(0f, -12f)),
                    PropertyFactory.iconAllowOverlap(true),
                    PropertyFactory.textAllowOverlap(true)
                ).withFilter(
                    Expression.eq(
                        Expression.literal("\$type"),
                        Expression.literal("Point")
                    )
                )

    private val polygonLayer: Layer
        get() = style.getLayer(POLYGON_LAYER_ID)
            ?: FillLayer(POLYGON_LAYER_ID, TEIS_SOURCE_ID)
                .withProperties(
                    PropertyFactory.fillColor(ColorUtils.withAlpha(enrollmentColor ?: -1))
                ).withFilter(
                    Expression.eq(
                        Expression.literal("\$type"),
                        Expression.literal("Polygon")
                    )
                )

    private val polygonBorderLayer: Layer
        get() = style.getLayer(POLYGON_BORDER_LAYER_ID)
            ?: LineLayer(POLYGON_BORDER_LAYER_ID, TEIS_SOURCE_ID)
                .withProperties(
                    PropertyFactory.lineColor(enrollmentDarkColor ?: -1),
                    PropertyFactory.lineWidth(2f)
                ).withFilter(
                    Expression.eq(
                        Expression.literal("\$type"),
                        Expression.literal("Polygon")
                    )
                )

    private val selectedPolygonLayer: Layer
        get() = style.getLayer(SELECTED_POLYGON_LAYER_ID)
            ?: FillLayer(SELECTED_POLYGON_LAYER_ID, SELECTED_POLYGON_SOURCE_ID)
                .withProperties(
                    PropertyFactory.fillColor(ColorUtils.withAlpha(enrollmentColor))
                ).withFilter(
                    Expression.eq(
                        Expression.literal("\$type"),
                        Expression.literal("Polygon")
                    )
                )

    private val selectedPolygonBorderLayer: Layer
        get() = style.getLayer(SELECTED_POLYGON_BORDER_LAYER_ID)
            ?: LineLayer(SELECTED_POLYGON_BORDER_LAYER_ID, SELECTED_POLYGON_SOURCE_ID)
                .withProperties(
                    PropertyFactory.lineColor(enrollmentDarkColor),
                    PropertyFactory.lineWidth(2f)
                ).withFilter(
                    Expression.eq(
                        Expression.literal("\$type"),
                        Expression.literal("Polygon")
                    )
                )

    private fun setVisibility(visibility: String) {
        when (featureType) {
            FeatureType.POINT -> {
                pointLayer.setProperties(PropertyFactory.visibility(visibility))
                selectedPointLayer.setProperties(PropertyFactory.visibility(visibility))
            }
            FeatureType.POLYGON -> {
                polygonLayer.setProperties(PropertyFactory.visibility(visibility))
                polygonBorderLayer.setProperties(PropertyFactory.visibility(visibility))
                selectedPolygonLayer.setProperties(PropertyFactory.visibility(visibility))
                selectedPolygonBorderLayer.setProperties(PropertyFactory.visibility(visibility))
            }
            else -> Unit
        }
        visible = visibility == Property.VISIBLE
    }

    override fun showLayer() {
        setVisibility(Property.VISIBLE)
    }

    override fun hideLayer() {
        setVisibility(Property.NONE)
    }

    override fun setSelectedItem(feature: Feature?) {
        feature?.let {
            if (it.geometry()?.type() == featureType.geometryType) {
                when (featureType) {
                    FeatureType.POINT -> selectPoint(it)
                    else -> selectPolygon(it)
                }
            }
        } ?: deselectCurrentPoint()
    }

    private fun selectPoint(feature: Feature) {
        style.getSourceAs<GeoJsonSource>(SELECTED_POINT_SOURCE_ID)?.apply {
            setGeoJson(feature)
        }

        selectedPointLayer.setProperties(
            PropertyFactory.iconSize(1.5f),
            PropertyFactory.visibility(Property.VISIBLE)
        )
    }

    private fun selectPolygon(feature: Feature) {
        style.getSourceAs<GeoJsonSource>(SELECTED_POLYGON_SOURCE_ID)?.apply {
            setGeoJson(feature)
        }

        selectedPolygonLayer.setProperties(
            PropertyFactory.fillColor(ColorUtils.withAlpha(enrollmentDarkColor)),
            PropertyFactory.visibility(Property.VISIBLE)
        )
        selectedPolygonBorderLayer.setProperties(
            PropertyFactory.lineColor(Color.WHITE),
            PropertyFactory.lineWidth(2.5f),
            PropertyFactory.visibility(Property.VISIBLE)
        )
    }

    private fun deselectCurrentPoint() {
        if (featureType == FeatureType.POINT) {
            selectedPointLayer.setProperties(
                PropertyFactory.visibility(Property.NONE)
            )
        } else {
            selectedPolygonLayer.setProperties(
                PropertyFactory.visibility(Property.NONE)
            )
            selectedPolygonBorderLayer.setProperties(
                PropertyFactory.visibility(Property.NONE)
            )
        }
    }

    override fun findFeatureWithUid(featureUidProperty: String): Feature? {
        return style.getSourceAs<GeoJsonSource>(TEIS_SOURCE_ID)
            ?.querySourceFeatures(Expression.eq(Expression.get("teiUid"), featureUidProperty))
            ?.firstOrNull()
            .also { setSelectedItem(it) }
    }

    override fun getId(): String {
        return if (featureType == FeatureType.POINT) {
            POINT_LAYER_ID
        } else {
            POLYGON_LAYER_ID
        }
    }
}
