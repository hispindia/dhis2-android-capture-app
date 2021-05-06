package org.dhis2afgamis.usescases.qrScanner

import dagger.Module
import dagger.Provides
import org.dhis2afgamis.data.dagger.PerActivity
import org.hisp.dhis.android.core.D2

@PerActivity
@Module
class ScanModule(private val optionSetUid: String?) {

    @Provides
    @PerActivity
    internal fun providesRepository(d2: D2): ScanRepository {
        return ScanRepository(d2, optionSetUid)
    }
}
