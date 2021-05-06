package org.fpandhis2.usescases.enrollment

import dagger.Subcomponent
import org.fpandhis2.data.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [EnrollmentModule::class])
interface EnrollmentComponent {
    fun inject(activity: EnrollmentActivity)
}
