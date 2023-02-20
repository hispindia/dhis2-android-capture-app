package org.dhis2_haparent.utils.customviews

import dagger.Subcomponent

@Subcomponent(modules = [CategoryComboDialogModule::class])
interface CategoryComboDialogComponent {
    fun inject(categoryOptionComboDialog: CategoryComboDialog)
}
