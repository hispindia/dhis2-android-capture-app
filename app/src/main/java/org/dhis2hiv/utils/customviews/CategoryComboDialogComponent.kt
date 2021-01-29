package org.dhis2hiv.utils.customviews

import dagger.Subcomponent

@Subcomponent(modules = [CategoryComboDialogModule::class])
interface CategoryComboDialogComponent {
    fun inject(categoryOptionComboDialog: CategoryComboDialog)
}
