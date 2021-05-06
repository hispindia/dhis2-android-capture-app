package org.dhis2afgamis.animations

import android.view.animation.DecelerateInterpolator
import org.dhis2afgamis.uicomponents.map.views.CarouselView

class CarouselViewAnimations {

    fun initMapLoading(view: CarouselView) {
        view.animate().apply {
            duration = 500
            interpolator = DecelerateInterpolator()
            alpha(0.25f)
            withStartAction { view.setEnabledStatus(false) }
            start()
        }
    }

    fun endMapLoading(view: CarouselView) {
        view.animate().apply {
            duration = 500
            interpolator = DecelerateInterpolator()
            alpha(1f)
            withEndAction { view.setEnabledStatus(true) }
            start()
        }
    }
}
