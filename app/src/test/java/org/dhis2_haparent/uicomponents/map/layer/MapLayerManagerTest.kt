package org.dhis2_haparent.uicomponents.map.layer

import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.dhis2_haparent.maps.layer.LayerType
import org.dhis2_haparent.maps.layer.basemaps.BaseMapManager
import org.dhis2_haparent.maps.layer.types.HeatmapMapLayer
import org.dhis2_haparent.maps.layer.types.RelationshipMapLayer
import org.dhis2_haparent.maps.layer.types.TeiMapLayer
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

class MapLayerManagerTest {

    private lateinit var mapLayerManager: org.dhis2_haparent.maps.layer.MapLayerManager
    private val sourceId = "sourceId"
    private val mapboxMap: MapboxMap = mock()
    private val mapStyle: org.dhis2_haparent.maps.model.MapStyle = mock()
    private val style: Style = mock()
    private val baseMapManager: BaseMapManager = mock()

    @Before
    fun setup() {
        mapLayerManager = org.dhis2_haparent.maps.layer.MapLayerManager(mapboxMap, baseMapManager)
    }

    @Test
    @Ignore
    fun `Should add layer with sourceId`() {
        whenever(mapboxMap.style) doReturn style
        mapLayerManager
            .addLayer(LayerType.TEI_LAYER, sourceId = sourceId)

        assert(mapLayerManager.mapLayers.isNotEmpty())
        assert(mapLayerManager.mapLayers[sourceId] is TeiMapLayer)
    }

    @Test
    @Ignore
    fun `Should add layer without sourceId`() {
        whenever(mapboxMap.style) doReturn style
        mapLayerManager
            .withMapStyle(mapStyle)
            .addLayer(LayerType.HEATMAP_LAYER)

        assert(mapLayerManager.mapLayers.isNotEmpty())
        assert(mapLayerManager.mapLayers[sourceId] is HeatmapMapLayer)
    }

    @Test
    @Ignore
    fun `Should add layers with sourceIds`() {
        val otherSourceId = "otherSourceId"
        whenever(mapboxMap.style) doReturn style
        mapLayerManager
            .addLayers(LayerType.RELATIONSHIP_LAYER, listOf(sourceId, otherSourceId), false)

        assert(mapLayerManager.mapLayers.isNotEmpty())
        assert(mapLayerManager.mapLayers[sourceId] is RelationshipMapLayer)
        assert(mapLayerManager.mapLayers[otherSourceId] is RelationshipMapLayer)
    }
}
