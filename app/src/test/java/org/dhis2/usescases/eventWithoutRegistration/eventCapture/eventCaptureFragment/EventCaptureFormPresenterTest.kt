package org.dhis2.usescases.eventWithoutRegistration.eventCapture.eventCaptureFragment

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import io.reactivex.processors.FlowableProcessor
import io.reactivex.subjects.BehaviorSubject
import org.dhis2.data.forms.dataentry.StoreResult
import org.dhis2.data.forms.dataentry.ValueStore
import org.dhis2.data.forms.dataentry.ValueStoreImpl
import org.dhis2.data.forms.dataentry.fields.ActionType
import org.dhis2.data.forms.dataentry.fields.FieldViewModel
import org.dhis2.data.forms.dataentry.fields.RowAction
import org.dhis2.data.schedulers.TrampolineSchedulerProvider
import org.dhis2.usescases.eventsWithoutRegistration.eventCapture.EventCaptureContract
import org.dhis2.usescases.eventsWithoutRegistration.eventCapture.eventCaptureFragment.EventCaptureFormPresenter
import org.dhis2.usescases.eventsWithoutRegistration.eventCapture.eventCaptureFragment.EventCaptureFormView
import org.hisp.dhis.android.core.D2
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times

class EventCaptureFormPresenterTest {
    private lateinit var presenter: EventCaptureFormPresenter
    private val activityPresenter: EventCaptureContract.Presenter = mock()
    private val view: EventCaptureFormView = mock()
    private val d2: D2 = Mockito.mock(D2::class.java, Mockito.RETURNS_DEEP_STUBS)
    private val valueStore: ValueStore = mock()
    private val schedulerProvider = TrampolineSchedulerProvider()
    private val onRowActionProcessor: FlowableProcessor<RowAction> = mock()

    @Before
    fun setUp() {
        presenter = EventCaptureFormPresenter(
            view,
            activityPresenter,
            d2,
            valueStore,
            schedulerProvider,
            onRowActionProcessor
        )
    }

    @Test
    fun `Should listen to data entry, sections and field changes`() {
        whenever(onRowActionProcessor.onBackpressureBuffer()) doReturn mock()
        whenever(
            onRowActionProcessor.onBackpressureBuffer().distinctUntilChanged()
        ) doReturn Flowable.just(RowAction("", "", type = ActionType.ON_SAVE))
        whenever(activityPresenter.formFieldsFlowable()) doReturn BehaviorSubject.create()
        presenter.init()

        assert(
            onRowActionProcessor
                .onBackpressureBuffer()
                .distinctUntilChanged()
                .test()
                .hasSubscription()
        )
        assert(activityPresenter.formFieldsFlowable().hasObservers())
    }

    @Test
    fun `Should save new value`() {
        whenever(onRowActionProcessor.onBackpressureBuffer()) doReturn mock()
        whenever(
            onRowActionProcessor.onBackpressureBuffer().distinctUntilChanged()
        ) doReturn Flowable.just(RowAction("testUid", "testValue", type = ActionType.ON_SAVE))
        whenever(activityPresenter.formFieldsFlowable()) doReturn BehaviorSubject.create()
        presenter.init()

        verify(valueStore, times(1)).save("testUid", "testValue")
    }

    @Test
    fun `Should ask for new calculation if value saved changed`() {
        whenever(onRowActionProcessor.onBackpressureBuffer()) doReturn mock()
        whenever(
            onRowActionProcessor.onBackpressureBuffer().distinctUntilChanged()
        ) doReturn Flowable.just(RowAction("testUid", "testValue", type = ActionType.ON_SAVE))
        whenever(activityPresenter.formFieldsFlowable()) doReturn BehaviorSubject.create()
        whenever(valueStore.save("testUid", "testValue")) doReturn Flowable.just(
            StoreResult("testUid", ValueStoreImpl.ValueStoreResult.VALUE_CHANGED)
        )
        presenter.init()

        verify(activityPresenter, times(1)).nextCalculation(true)
    }

    @Test
    fun `Should not ask for new calculation if value saved did not changed`() {
        val subject = BehaviorSubject.create<List<FieldViewModel>>()
        whenever(onRowActionProcessor.onBackpressureBuffer()) doReturn mock()
        whenever(
            onRowActionProcessor.onBackpressureBuffer().distinctUntilChanged()
        ) doReturn Flowable.just(RowAction("testUid", "testValue", type = ActionType.ON_SAVE))
        whenever(activityPresenter.formFieldsFlowable()) doReturn subject
        whenever(valueStore.save("testUid", "testValue")) doReturn Flowable.just(
            StoreResult("testUid", ValueStoreImpl.ValueStoreResult.VALUE_HAS_NOT_CHANGED)
        )
        presenter.init()

        verify(activityPresenter, times(0)).nextCalculation(true)
    }

    @Test
    fun `Should show fields`() {
        whenever(onRowActionProcessor.onBackpressureBuffer()) doReturn mock()
        whenever(
            onRowActionProcessor.onBackpressureBuffer().distinctUntilChanged()
        ) doReturn Flowable.just(RowAction("", "", type = ActionType.ON_SAVE))
        whenever(activityPresenter.formFieldsFlowable()) doReturn BehaviorSubject.create()
        presenter.init()
        activityPresenter.formFieldsFlowable().onNext(mutableListOf())
        verify(view, times(1)).showFields(any())
    }

    @Test
    fun `Should clear disposable`() {
        whenever(onRowActionProcessor.onBackpressureBuffer()) doReturn mock()
        whenever(
            onRowActionProcessor.onBackpressureBuffer().distinctUntilChanged()
        ) doReturn Flowable.just(RowAction("", "", type = ActionType.ON_SAVE))
        whenever(activityPresenter.formFieldsFlowable()) doReturn BehaviorSubject.create()
        presenter.init()
        presenter.onDetach()
        assert(presenter.disposable.size() == 0)
    }

    @Test
    fun `Should try to finish`() {
        presenter.onActionButtonClick()
        verify(activityPresenter).attempFinish()
    }
}
