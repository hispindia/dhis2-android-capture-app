package org.dhis2_haparent.commons.dialogs.calendarpicker

interface CalendarPickerRepository {
    fun isDatePickerStyle(): Boolean
    fun setPickerStyle(isDatePicker: Boolean)
}
