package org.dhis2afgamis.usescases.orgunitselector

import dagger.Subcomponent
import org.dhis2afgamis.data.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [OUTreeModule::class])
interface OUTreeComponent {
    fun inject(activity: OUTreeActivity)
}
