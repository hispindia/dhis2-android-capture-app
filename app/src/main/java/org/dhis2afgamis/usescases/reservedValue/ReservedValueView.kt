package org.dhis2afgamis.usescases.reservedValue

import org.dhis2afgamis.usescases.general.AbstractActivityContracts

interface ReservedValueView : AbstractActivityContracts.View {
    fun setReservedValues(reservedValueModels: List<ReservedValueModel>)
    fun onBackClick()
    fun showReservedValuesError()
}
