package org.dhis2_haparent.commons.dialogs.calendarpicker

import android.widget.DatePicker

interface OnDatePickerListener {
    fun onNegativeClick()
    fun onPositiveClick(datePicker: DatePicker)
}
