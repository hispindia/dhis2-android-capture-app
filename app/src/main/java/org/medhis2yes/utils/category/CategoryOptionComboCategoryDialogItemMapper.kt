package org.medhis2yes.utils.category

import org.hisp.dhis.android.core.category.CategoryOptionCombo

class CategoryOptionComboCategoryDialogItemMapper {

    fun map(categoryOptionCombo: CategoryOptionCombo): CategoryDialogItem {
        return CategoryDialogItem(
            categoryOptionCombo.uid(),
            categoryOptionCombo.displayName() ?: "-"
        )
    }
}
