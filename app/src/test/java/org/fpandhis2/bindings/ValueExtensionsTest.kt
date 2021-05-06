package org.fpandhis2.bindings

import org.fpandhis2.Bindings.withValueTypeCheck
import org.hisp.dhis.android.core.common.ValueType
import org.junit.Assert.assertTrue
import org.junit.Test

class ValueExtensionsTest {
    @Test
    fun `Should parse to correct integer format`() {
        val valueList: List<String?> = arrayListOf(
            "",
            "0.0",
            "1.2",
            "1",
            "1234",
            "214748999",
            null
        )

        val expectedResults = arrayListOf(
            "",
            "0",
            "1",
            "1",
            "1234",
            "214748999",
            null
        )

        valueList.forEachIndexed { index, value ->
            assertTrue(
                value.withValueTypeCheck(ValueType.INTEGER) == expectedResults[index]
            )
        }
    }

    @Test
    fun `Should parse to correct unit interval format`() {
        val valueList: List<String?> = arrayListOf(
            "",
            "0",
            ".2233",
            "1",
            null
        )

        val expectedResults = arrayListOf(
            "",
            "0.0",
            "0.2233",
            "1.0",
            null
        )

        valueList.forEachIndexed { index, value ->
            assertTrue(
                value.withValueTypeCheck(ValueType.UNIT_INTERVAL) == expectedResults[index]
            )
        }
    }
}
