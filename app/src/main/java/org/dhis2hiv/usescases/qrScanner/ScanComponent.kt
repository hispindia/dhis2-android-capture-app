package org.dhis2hiv.usescases.qrScanner

import dagger.Subcomponent
import org.dhis2hiv.data.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [ScanModule::class])
interface ScanComponent {
    fun inject(scanActivity: ScanActivity?)
}
