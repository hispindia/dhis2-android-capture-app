package org.dhis2afgamis.usescases.qrScanner

import dagger.Subcomponent
import org.dhis2afgamis.data.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [ScanModule::class])
interface ScanComponent {
    fun inject(scanActivity: ScanActivity?)
}
