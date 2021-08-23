package org.medhis2yes.usescases.qrScanner

import dagger.Subcomponent
import org.medhis2yes.data.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [ScanModule::class])
interface ScanComponent {
    fun inject(scanActivity: ScanActivity?)
}
