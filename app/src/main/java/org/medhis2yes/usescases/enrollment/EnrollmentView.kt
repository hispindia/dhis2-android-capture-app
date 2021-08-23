package org.medhis2yes.usescases.enrollment

import org.medhis2yes.form.model.FieldUiModel
import org.medhis2yes.usescases.general.AbstractActivityContracts
import org.medhis2yes.utils.RulesUtilsProviderConfigurationError
import org.hisp.dhis.android.core.enrollment.EnrollmentStatus

interface EnrollmentView : AbstractActivityContracts.View {

    fun setAccess(access: Boolean?)

    fun renderStatus(status: EnrollmentStatus)
    fun showStatusOptions(currentStatus: EnrollmentStatus)

    fun showFields(fields: List<FieldUiModel>)

    fun setSaveButtonVisible(visible: Boolean)

    fun displayTeiInfo(attrList: List<String>, profileImage: String)
    fun openEvent(eventUid: String)
    fun openDashboard(enrollmentUid: String)
    fun goBack()
    fun showMissingMandatoryFieldsMessage(emptyMandatoryFields: MutableMap<String, String>)
    fun showErrorFieldsMessage(errorFields: List<String>)
    fun setResultAndFinish()
    fun requestFocus()
    fun performSaveClick()
    fun showProgress()
    fun hideProgress()
    fun displayTeiPicture(picturePath: String)
    fun showDateEditionWarning()
    fun displayConfigurationErrors(configurationError: List<RulesUtilsProviderConfigurationError>)
}
