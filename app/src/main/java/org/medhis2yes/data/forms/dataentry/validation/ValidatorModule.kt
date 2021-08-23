package org.medhis2yes.data.forms.dataentry.validation

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import org.medhis2yes.data.dagger.ValueTypeKey
import org.medhis2yes.utils.Validator
import org.hisp.dhis.android.core.common.ValueType

@Module
class ValidatorModule {

    @Provides
    @IntoMap
    @ValueTypeKey(ValueType.INTEGER_POSITIVE)
    fun provideIntegerPositiveValidator(): Validator = PositiveIntegerValidator()

    @Provides
    @IntoMap
    @ValueTypeKey(ValueType.INTEGER_NEGATIVE)
    fun provideIntegerNegativeValidator(): Validator = NegativeIntegerValidator()

    @Provides
    @IntoMap
    @ValueTypeKey(ValueType.INTEGER_ZERO_OR_POSITIVE)
    fun provideIntegerZeroOrPositiveValidator(): Validator = ZeroOrPositiveIntegerValidator()

    @Provides
    @IntoMap
    @ValueTypeKey(ValueType.INTEGER)
    fun provideIntegerValidator(): Validator = IntegerValidator()

    @Provides
    @IntoMap
    @ValueTypeKey(ValueType.NUMBER)
    fun provideNumberValidator(): Validator = NumberValidator()
}
