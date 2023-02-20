package org.dhis2_haparent.maps.carousel

internal interface CarouselBinder<T> {
    fun bind(data: T)
    fun showNavigateButton()
    fun hideNavigateButton()
}
