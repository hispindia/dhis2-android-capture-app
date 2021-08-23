package org.medhis2yes.usescases.main.program

import dagger.Subcomponent
import org.medhis2yes.data.dagger.PerFragment

/**
 * QUADRAM. Created by ppajuelo on 07/02/2018.
 */
@PerFragment
@Subcomponent(modules = [ProgramModule::class])
interface ProgramComponent {
    fun inject(programFragment: ProgramFragment)
}
