package org.medhis2yes.form.ui.style

data class BasicFormUiModelStyle(val factory: FormUiColorFactory) : FormUiModelStyle {
    private var colors = factory.getBasicColors()

    override fun getColors(): Map<FormUiColorType, Int> {
        return colors
    }
}
