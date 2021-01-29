package org.dhis2hiv.uicomponents.map.carousel

internal interface CarouselBinder<T> {
    fun bind(data: T)
}
