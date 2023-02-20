package org.dhis2_haparent.utils.category

import org.hisp.dhis.android.core.category.CategoryOption

class CategoryOptionCategoryDialogItemMapper {

    fun map(categoryOption: CategoryOption): CategoryDialogItem {
        return CategoryDialogItem(
            categoryOption.uid(),
            categoryOption.displayName() ?: "-"
        )
    }
}
