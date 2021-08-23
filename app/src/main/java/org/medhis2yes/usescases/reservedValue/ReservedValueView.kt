package org.medhis2yes.usescases.reservedValue

import org.medhis2yes.usescases.general.AbstractActivityContracts

interface ReservedValueView : AbstractActivityContracts.View {
    fun setReservedValues(reservedValueModels: List<ReservedValueModel>)
    fun onBackClick()
    fun showReservedValuesError()
}
