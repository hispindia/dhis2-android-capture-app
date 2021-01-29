package org.dhis2hiv.data.enrollment

import android.content.Context
import org.dhis2hiv.R
import org.hisp.dhis.android.core.enrollment.EnrollmentStatus

class EnrollmentUiDataHelper(val context: Context) {
    fun getEnrollmentStatusClientName(enrollmentStatus: EnrollmentStatus): String {
        return when (enrollmentStatus) {
            EnrollmentStatus.ACTIVE -> context.getString(R.string.enrollment_status_active)
            EnrollmentStatus.COMPLETED -> context.getString(R.string.enrollment_status_completed)
            EnrollmentStatus.CANCELLED -> context.getString(R.string.enrollment_status_cancelled)
        }
    }
}
