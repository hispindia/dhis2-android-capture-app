package org.dhis2_haparent.usescases.qrScanner

import dagger.Subcomponent
import org.dhis2_haparent.commons.di.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [ScanModule::class])
interface ScanComponent {
    fun inject(scanActivity: ScanActivity?)
}
