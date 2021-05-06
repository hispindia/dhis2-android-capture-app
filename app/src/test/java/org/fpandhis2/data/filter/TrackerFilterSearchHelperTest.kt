package org.fpandhis2.data.filter

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import java.util.Date
import org.fpandhis2.utils.filters.FilterManager
import org.fpandhis2.utils.filters.Filters
import org.fpandhis2.utils.filters.sorting.SortingItem
import org.fpandhis2.utils.filters.sorting.SortingStatus
import org.hisp.dhis.android.core.common.State
import org.hisp.dhis.android.core.enrollment.EnrollmentStatus
import org.hisp.dhis.android.core.event.EventStatus
import org.hisp.dhis.android.core.organisationunit.OrganisationUnit
import org.hisp.dhis.android.core.period.DatePeriod
import org.hisp.dhis.android.core.trackedentity.search.TrackedEntityInstanceQueryCollectionRepository
import org.junit.After
import org.junit.Before
import org.junit.Test

class TrackerFilterSearchHelperTest {

    private lateinit var trackerFilterSearchHelper: TrackerFilterSearchHelper
    private val filterRepository: FilterRepository = mock()
    private val filterManager: FilterManager = FilterManager.getInstance()

    @Before
    fun setUp() {
        trackerFilterSearchHelper = TrackerFilterSearchHelper(
            filterRepository,
            filterManager
        )
        whenever(
            filterRepository.trackedEntityInstanceQueryByProgram(any())
        ) doReturn mock()
        whenever(
            filterRepository.trackedEntityInstanceQueryByType(any())
        ) doReturn mock()
        whenever(
            filterRepository.applyOrgUnitFilter(any(), any(), any())
        ) doReturn mock()
    }

    @After
    fun clearAll() {
        filterManager.clearAllFilters()
    }

    @Test
    fun `Should return query by program`() {
        trackerFilterSearchHelper.getFilteredProgramRepository("programUid")
        verify(filterRepository).trackedEntityInstanceQueryByProgram("programUid")
    }

    @Test
    fun `Should return query by type`() {
        trackerFilterSearchHelper.getFilteredTrackedEntityTypeRepository("teType")
        verify(filterRepository).trackedEntityInstanceQueryByType("teType")
    }

    @Test
    fun `Should not apply any filters if not set`() {
        trackerFilterSearchHelper.getFilteredProgramRepository("programUid")
        verify(filterRepository, times(0)).applyEnrollmentStatusFilter(any(), any())
        verify(filterRepository, times(0)).applyEventStatusFilter(any(), any())
        verify(
            filterRepository,
            times(0)
        ).applyStateFilter(any<TrackedEntityInstanceQueryCollectionRepository>(), any())
        verify(
            filterRepository,
            times(0)
        ).applyDateFilter(any<TrackedEntityInstanceQueryCollectionRepository>(), any())
        verify(filterRepository, times(0)).applyEnrollmentDateFilter(any(), any())
        verify(
            filterRepository,
            times(0)
        ).applyAssignToMe(any<TrackedEntityInstanceQueryCollectionRepository>())
    }

    @Test
    fun `Should apply filters if set`() {
        filterManager.apply {
            addEnrollmentStatus(false, EnrollmentStatus.ACTIVE)
            addEventStatus(false, EventStatus.SCHEDULE)
            addOrgUnit(
                OrganisationUnit.builder()
                    .uid("ouUid").build()
            )
            addState(false, State.ERROR)
            addPeriod(arrayListOf(DatePeriod.create(Date(), Date())))
            addEnrollmentPeriod(arrayListOf(DatePeriod.create(Date(), Date())))
            setAssignedToMe(true)
        }

        whenever(filterRepository.applyEnrollmentStatusFilter(any(), any())) doReturn mock()
        whenever(filterRepository.applyEventStatusFilter(any(), any())) doReturn mock()
        whenever(filterRepository.applyOrgUnitFilter(any(), any(), any())) doReturn mock()
        whenever(
            filterRepository.applyStateFilter(
                any<TrackedEntityInstanceQueryCollectionRepository>(),
                any()
            )
        ) doReturn mock()
        whenever(
            filterRepository.applyDateFilter(
                any<TrackedEntityInstanceQueryCollectionRepository>(),
                any()
            )
        ) doReturn mock()
        whenever(
            filterRepository.applyEnrollmentDateFilter(any(), any())
        ) doReturn mock()
        whenever(
            filterRepository.applyAssignToMe(any<TrackedEntityInstanceQueryCollectionRepository>())
        ) doReturn mock()
        trackerFilterSearchHelper.getFilteredProgramRepository("programUid")

        verify(filterRepository, times(1)).applyEnrollmentStatusFilter(any(), any())
        verify(filterRepository, times(1)).applyEventStatusFilter(any(), any())
        verify(filterRepository, times(1)).applyOrgUnitFilter(
            any(),
            any(),
            any()
        )
        verify(
            filterRepository,
            times(1)
        ).applyStateFilter(any<TrackedEntityInstanceQueryCollectionRepository>(), any())
        verify(
            filterRepository,
            times(1)
        ).applyDateFilter(any<TrackedEntityInstanceQueryCollectionRepository>(), any())
        verify(filterRepository, times(1)).applyEnrollmentDateFilter(any(), any())
        verify(
            filterRepository,
            times(1)
        ).applyAssignToMe(any<TrackedEntityInstanceQueryCollectionRepository>())
    }

    @Test
    fun `Should apply sorting for supported sorting type`() {
        filterManager.sortingItem = SortingItem(Filters.PERIOD, SortingStatus.ASC)
        trackerFilterSearchHelper.getFilteredProgramRepository("programUid")
        verify(filterRepository, times(1)).sortByPeriod(any(), any())
    }
}
