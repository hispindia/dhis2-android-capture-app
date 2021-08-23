package org.medhis2yes.usescases.enrollment

import dagger.Subcomponent
import org.medhis2yes.data.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [EnrollmentModule::class])
interface EnrollmentComponent {
    fun inject(activity: EnrollmentActivity)
}
