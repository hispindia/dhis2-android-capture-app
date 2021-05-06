package org.fpandhis2.usescases.reservedValue

import org.fpandhis2.usescases.general.AbstractActivityContracts

interface ReservedValueView : AbstractActivityContracts.View {
    fun setReservedValues(reservedValueModels: List<ReservedValueModel>)
    fun onBackClick()
    fun showReservedValuesError()
}
