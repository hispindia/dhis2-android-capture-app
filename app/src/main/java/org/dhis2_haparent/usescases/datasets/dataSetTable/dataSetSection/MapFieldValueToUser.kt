package org.dhis2_haparent.usescases.datasets.dataSetTable.dataSetSection

import org.dhis2_haparent.Bindings.toDate
import org.dhis2_haparent.R
import org.dhis2_haparent.commons.resources.ResourceManager
import org.dhis2_haparent.data.forms.dataentry.tablefields.FieldViewModel
import org.dhis2_haparent.utils.DateUtils
import org.hisp.dhis.android.core.common.ValueType
import org.hisp.dhis.android.core.dataelement.DataElement

class MapFieldValueToUser(
    val resources: ResourceManager,
    val repository: DataValueRepository
) {

    fun map(field: FieldViewModel, dataElement: DataElement): String? {
        return when (dataElement.valueType()) {
            ValueType.BOOLEAN,
            ValueType.TRUE_ONLY -> {
                if (!field.value().isNullOrEmpty()) {
                    if (field.value().toBoolean()) {
                        resources.getString(R.string.yes)
                    } else {
                        resources.getString(R.string.no)
                    }
                } else {
                    field.value()
                }
            }
            ValueType.AGE -> {
                if (!field.value().isNullOrEmpty()) {
                    DateUtils.uiDateFormat().format(field.value()!!.toDate())
                } else {
                    field.value()
                }
            }
            ValueType.IMAGE,
            ValueType.FILE_RESOURCE,
            ValueType.TRACKER_ASSOCIATE,
            ValueType.REFERENCE,
            ValueType.USERNAME,
            ValueType.GEOJSON -> resources.getString(R.string.unsupported_value_type)
            ValueType.ORGANISATION_UNIT -> {
                if (!field.value().isNullOrEmpty()) {
                    repository.getOrgUnitById(field.value()!!)
                } else {
                    field.value()
                }
            }
            else -> field.value()
        }
    }

    fun getDefaultHeaderLabel(): String {
        return resources.defaultTableLabel()
    }
}
