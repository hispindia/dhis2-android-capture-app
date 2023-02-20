package org.dhis2_haparent.maps.layer.basemaps

import android.graphics.drawable.Drawable

data class BaseMap(
    val baseMapStyle: BaseMapStyle,
    val basemapName: String,
    val basemapImage: Drawable?
)
