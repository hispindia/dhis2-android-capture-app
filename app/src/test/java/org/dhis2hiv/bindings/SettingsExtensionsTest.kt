package org.dhis2hiv.bindings

import org.dhis2hiv.Bindings.EVERY_12_HOUR
import org.dhis2hiv.Bindings.EVERY_24_HOUR
import org.dhis2hiv.Bindings.EVERY_30_MIN
import org.dhis2hiv.Bindings.EVERY_6_HOUR
import org.dhis2hiv.Bindings.EVERY_7_DAYS
import org.dhis2hiv.Bindings.EVERY_HOUR
import org.dhis2hiv.Bindings.toSeconds
import org.hisp.dhis.android.core.settings.DataSyncPeriod
import org.hisp.dhis.android.core.settings.MetadataSyncPeriod
import org.junit.Test

class SettingsExtensionsTest {

    private val metadataSyncingPeriods = arrayListOf(
        EVERY_HOUR,
        EVERY_12_HOUR,
        EVERY_24_HOUR,
        EVERY_7_DAYS,
        0
    )

    private val dataSyncingPeriods = arrayListOf(
        EVERY_30_MIN,
        EVERY_HOUR,
        EVERY_6_HOUR,
        EVERY_12_HOUR,
        EVERY_24_HOUR,
        0
    )

    @Test
    fun `Metadata sync period must transform to proper integer`() {
        for (index in MetadataSyncPeriod.values().indices) {
            assert(MetadataSyncPeriod.values()[index].toSeconds() == metadataSyncingPeriods[index])
        }
    }

    @Test
    fun `Data sync period must transform to proper integer`() {
        for (index in DataSyncPeriod.values().indices) {
            assert(DataSyncPeriod.values()[index].toSeconds() == dataSyncingPeriods[index])
        }
    }
}
