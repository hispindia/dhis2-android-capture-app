package org.dhis2hiv.usescases.orgunitselector

import dagger.Subcomponent
import org.dhis2hiv.data.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [OUTreeModule::class])
interface OUTreeComponent {
    fun inject(activity: OUTreeActivity)
}
