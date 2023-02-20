package org.dhis2_haparent.usescases.about

import dagger.Subcomponent
import org.dhis2_haparent.commons.di.dagger.PerFragment

@PerFragment
@Subcomponent(modules = [AboutModule::class])
interface AboutComponent {
    fun inject(programFragment: AboutFragment?)
}
