package org.dhis2_haparent.usescases.reservedValue

import org.dhis2_haparent.usescases.general.AbstractActivityContracts

interface ReservedValueView : AbstractActivityContracts.View {
    fun setReservedValues(reservedValueModels: List<ReservedValueModel>)
    fun onBackClick()
    fun showReservedValuesError()
}
