package org.dhis2_haparent.form.ui.provider

import org.dhis2_haparent.form.model.LegendValue

interface LegendValueProvider {

    fun provideLegendValue(
        dataElementUid: String,
        value: String?
    ): LegendValue?
}
