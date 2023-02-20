package org.dhis2_haparent.form.ui.provider

import org.dhis2_haparent.form.model.KeyboardActionType
import org.hisp.dhis.android.core.common.ValueType

interface KeyboardActionProvider {

    fun provideKeyboardAction(valueType: ValueType): KeyboardActionType?
}
