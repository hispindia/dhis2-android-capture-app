package org.fpandhis2.usescases.qrScanner

import dagger.Subcomponent
import org.fpandhis2.data.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [ScanModule::class])
interface ScanComponent {
    fun inject(scanActivity: ScanActivity?)
}
