package org.medhis2yes.form.ui.intent

import org.medhis2yes.form.mvi.MviIntent

sealed class FormIntent : MviIntent {
    data class SelectDateFromAgeCalendar(val uid: String, val date: String?) : FormIntent()
    data class ClearDateFromAgeCalendar(val uid: String) : FormIntent()
}
