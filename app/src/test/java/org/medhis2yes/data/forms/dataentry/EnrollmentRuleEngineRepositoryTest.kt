package org.medhis2yes.data.forms.dataentry

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import org.medhis2yes.data.forms.FormRepository
import org.hisp.dhis.android.core.D2
import org.junit.Test
import org.mockito.Mockito

class EnrollmentRuleEngineRepositoryTest {
    private val formRepository: FormRepository = mock()
    private val d2: D2 = Mockito.mock(D2::class.java, Mockito.RETURNS_DEEP_STUBS)

    @Test
    fun `Should not init data if enrollmentUid is empty`() {
        EnrollmentRuleEngineRepository(formRepository, "", d2)
        verifyZeroInteractions(d2)
    }
}
