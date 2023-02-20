package org.dhis2_haparent.form.ui.provider

import org.hisp.dhis.android.core.common.ValueType

interface DisplayNameProvider {

    fun provideDisplayName(
        valueType: ValueType?,
        value: String?,
        optionSet: String? = null
    ): String?
}
