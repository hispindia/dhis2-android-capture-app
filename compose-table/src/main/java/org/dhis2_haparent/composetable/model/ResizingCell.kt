package org.dhis2_haparent.composetable.model

import androidx.compose.ui.geometry.Offset

data class ResizingCell(
    val initialPosition: Offset,
    val draggingOffsetX: Float
)
