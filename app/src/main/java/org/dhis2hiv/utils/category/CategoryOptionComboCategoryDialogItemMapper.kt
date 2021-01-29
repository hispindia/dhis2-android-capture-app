package org.dhis2hiv.utils.category

import org.hisp.dhis.android.core.category.CategoryOptionCombo

class CategoryOptionComboCategoryDialogItemMapper {

    fun map(categoryOptionCombo: CategoryOptionCombo): CategoryDialogItem {
        return CategoryDialogItem(
            categoryOptionCombo.uid(),
            categoryOptionCombo.displayName() ?: "-"
        )
    }
}
