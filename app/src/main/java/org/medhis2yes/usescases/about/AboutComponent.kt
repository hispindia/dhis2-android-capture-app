package org.medhis2yes.usescases.about

import dagger.Subcomponent
import org.medhis2yes.data.dagger.PerFragment

@PerFragment
@Subcomponent(modules = [AboutModule::class])
interface AboutComponent {
    fun inject(programFragment: AboutFragment?)
}
