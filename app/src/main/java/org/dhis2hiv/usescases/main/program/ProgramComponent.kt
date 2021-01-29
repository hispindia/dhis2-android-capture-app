package org.dhis2hiv.usescases.main.program

import dagger.Subcomponent
import org.dhis2hiv.data.dagger.PerFragment

/**
 * QUADRAM. Created by ppajuelo on 07/02/2018.
 */
@PerFragment
@Subcomponent(modules = [ProgramModule::class])
interface ProgramComponent {
    fun inject(programFragment: ProgramFragment)
}
