package org.medhis2yes.uicomponents.map.carousel

internal interface CarouselBinder<T> {
    fun bind(data: T)
    fun showNavigateButton()
    fun hideNavigateButton()
}
