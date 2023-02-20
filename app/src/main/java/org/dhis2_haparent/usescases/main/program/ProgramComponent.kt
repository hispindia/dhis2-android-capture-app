package org.dhis2_haparent.usescases.main.program

import dagger.Subcomponent
import org.dhis2_haparent.commons.di.dagger.PerFragment

/**
 * QUADRAM. Created by ppajuelo on 07/02/2018.
 */
@PerFragment
@Subcomponent(modules = [ProgramModule::class])
interface ProgramComponent {
    fun inject(programFragment: ProgramFragment)
}
