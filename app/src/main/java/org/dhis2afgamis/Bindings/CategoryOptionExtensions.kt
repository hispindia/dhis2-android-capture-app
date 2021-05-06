package org.dhis2afgamis.Bindings

import java.util.Date
import org.hisp.dhis.android.core.category.CategoryOption

fun CategoryOption.inDateRange(date: Date?): Boolean {
    return date?.let {
        (startDate() == null || date.after(startDate())) &&
            (endDate() == null || date.before(endDate()))
    } ?: true
}
