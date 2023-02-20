package org.dhis2_haparent.commons.filters.di

import org.dhis2_haparent.commons.filters.data.FilterPresenter

interface FilterPresenterProvider {
    fun provideFilterPresenter(): FilterPresenter?
}
