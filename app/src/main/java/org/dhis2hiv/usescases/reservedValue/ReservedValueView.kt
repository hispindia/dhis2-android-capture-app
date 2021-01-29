package org.dhis2hiv.usescases.reservedValue

import org.dhis2hiv.usescases.general.AbstractActivityContracts

interface ReservedValueView : AbstractActivityContracts.View {
    fun setReservedValues(reservedValueModels: List<ReservedValueModel>)
    fun onBackClick()
    fun showReservedValuesError()
}
