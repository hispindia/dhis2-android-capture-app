package org.dhis2_haparent.usescases.enrollment

import org.dhis2_haparent.usescases.general.AbstractActivityContracts
import org.hisp.dhis.android.core.enrollment.EnrollmentStatus

interface EnrollmentView : AbstractActivityContracts.View {

    fun setAccess(access: Boolean?)

    fun renderStatus(status: EnrollmentStatus)
    fun showStatusOptions(currentStatus: EnrollmentStatus)

    fun setSaveButtonVisible(visible: Boolean)

    fun displayTeiInfo(attrList: List<String>, profileImage: String)
    fun openEvent(eventUid: String)
    fun openDashboard(enrollmentUid: String)
    fun goBack()
    fun setResultAndFinish()
    fun requestFocus()
    fun performSaveClick()
    fun showProgress()
    fun hideProgress()
    fun displayTeiPicture(picturePath: String)
    fun showDateEditionWarning()
}
