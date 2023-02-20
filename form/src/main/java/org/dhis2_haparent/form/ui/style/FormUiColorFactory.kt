package org.dhis2_haparent.form.ui.style

interface FormUiColorFactory {
    fun getBasicColors(): Map<FormUiColorType, Int>
}
