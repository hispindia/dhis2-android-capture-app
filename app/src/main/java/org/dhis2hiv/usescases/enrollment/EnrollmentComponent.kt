package org.dhis2hiv.usescases.enrollment

import dagger.Subcomponent
import org.dhis2hiv.data.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [EnrollmentModule::class])
interface EnrollmentComponent {
    fun inject(activity: EnrollmentActivity)
}
