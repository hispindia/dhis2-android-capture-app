package org.fpandhis2.utils.category

import org.hisp.dhis.android.core.category.CategoryOptionCombo

class CategoryOptionComboCategoryDialogItemMapper {

    fun map(categoryOptionCombo: CategoryOptionCombo): CategoryDialogItem {
        return CategoryDialogItem(
            categoryOptionCombo.uid(),
            categoryOptionCombo.displayName() ?: "-"
        )
    }
}
