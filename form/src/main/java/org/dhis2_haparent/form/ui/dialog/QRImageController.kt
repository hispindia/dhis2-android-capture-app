package org.dhis2_haparent.form.ui.dialog

import android.graphics.Bitmap
import org.dhis2_haparent.form.model.UiRenderType

interface QRImageController {

    fun writeDataToImage(
        value: String,
        renderingType: UiRenderType
    ): Bitmap
}
