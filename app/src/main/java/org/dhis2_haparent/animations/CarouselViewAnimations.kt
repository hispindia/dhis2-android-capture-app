package org.dhis2_haparent.animations

import android.view.animation.DecelerateInterpolator

class CarouselViewAnimations {

    fun initMapLoading(view: org.dhis2_haparent.maps.views.CarouselView) {
        view.animate().apply {
            duration = 500
            interpolator = DecelerateInterpolator()
            alpha(0.25f)
            withStartAction { view.setEnabledStatus(false) }
            start()
        }
    }

    fun endMapLoading(view: org.dhis2_haparent.maps.views.CarouselView) {
        view.animate().apply {
            duration = 500
            interpolator = DecelerateInterpolator()
            alpha(1f)
            withEndAction {
                view.setEnabledStatus(true)
                view.selectFirstItem()
            }
            start()
        }
    }
}
