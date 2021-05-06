package org.dhis2afgamis.usescases.enrollment

import dagger.Subcomponent
import org.dhis2afgamis.data.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [EnrollmentModule::class])
interface EnrollmentComponent {
    fun inject(activity: EnrollmentActivity)
}
