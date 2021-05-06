package org.fpandhis2.usescases.orgunitselector

import dagger.Subcomponent
import org.fpandhis2.data.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [OUTreeModule::class])
interface OUTreeComponent {
    fun inject(activity: OUTreeActivity)
}
