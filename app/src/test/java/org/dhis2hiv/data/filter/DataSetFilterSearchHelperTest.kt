package org.dhis2hiv.data.filter

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import java.util.Date
import org.dhis2hiv.utils.filters.FilterManager
import org.hisp.dhis.android.core.common.State
import org.hisp.dhis.android.core.dataset.DataSetInstanceSummaryCollectionRepository
import org.hisp.dhis.android.core.organisationunit.OrganisationUnit
import org.hisp.dhis.android.core.period.DatePeriod
import org.junit.After
import org.junit.Before
import org.junit.Test

class DataSetFilterSearchHelperTest {

    private lateinit var dataSetFilterSearchHelper: DataSetFilterSearchHelper
    private val filterRepository: FilterRepository = mock()
    private val filterManager: FilterManager = FilterManager.getInstance()

    @Before
    fun setUp() {
        dataSetFilterSearchHelper = DataSetFilterSearchHelper(
            filterRepository,
            filterManager
        )
        whenever(
            filterRepository.dataSetInstanceSummaries()
        ) doReturn mock()
    }

    @After
    fun clearAll() {
        filterManager.clearAllFilters()
    }

    @Test
    fun `Should return dataset instance summaries`() {
        dataSetFilterSearchHelper.getFilteredDataSetSearchRepository()
        verify(filterRepository).dataSetInstanceSummaries()
    }

    @Test
    fun `Should not apply any filters if not set`() {
        dataSetFilterSearchHelper.getFilteredDataSetSearchRepository()
        verify(filterRepository, times(0))
            .applyOrgUnitFilter(any<DataSetInstanceSummaryCollectionRepository>(), any())
        verify(filterRepository, times(0))
            .applyStateFilter(any<DataSetInstanceSummaryCollectionRepository>(), any())
        verify(filterRepository, times(0))
            .applyPeriodFilter(any(), any())
    }

    @Test
    fun `Should apply filters if set`() {
        filterManager.apply {
            addOrgUnit(
                OrganisationUnit.builder()
                    .uid("ouUid").build()
            )
            addState(false, State.ERROR)
            addPeriod(arrayListOf(DatePeriod.create(Date(), Date())))
        }

        whenever(
            filterRepository.applyOrgUnitFilter(
                any<DataSetInstanceSummaryCollectionRepository>(),
                any()
            )
        ) doReturn mock()
        whenever(
            filterRepository.applyStateFilter(
                any<DataSetInstanceSummaryCollectionRepository>(),
                any()
            )
        ) doReturn mock()
        whenever(
            filterRepository.applyPeriodFilter(any(), any())
        ) doReturn mock()

        dataSetFilterSearchHelper.getFilteredDataSetSearchRepository()

        verify(filterRepository, times(1))
            .applyOrgUnitFilter(any<DataSetInstanceSummaryCollectionRepository>(), any())
        verify(filterRepository, times(1))
            .applyStateFilter(any<DataSetInstanceSummaryCollectionRepository>(), any())
        verify(filterRepository, times(1))
            .applyPeriodFilter(any(), any())
    }
}
