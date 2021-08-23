package org.medhis2yes.usescases.eventsWithoutRegistration.eventCapture.eventCaptureFragment

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import io.reactivex.processors.FlowableProcessor
import io.reactivex.subjects.BehaviorSubject
import org.medhis2yes.data.schedulers.TrampolineSchedulerProvider
import org.medhis2yes.form.data.FormRepository
import org.medhis2yes.form.model.ActionType
import org.medhis2yes.form.model.FieldUiModel
import org.medhis2yes.form.model.RowAction
import org.medhis2yes.form.model.StoreResult
import org.medhis2yes.form.model.ValueStoreResult
import org.medhis2yes.usescases.eventsWithoutRegistration.eventCapture.EventCaptureContract
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.times

class EventCaptureFormPresenterTest {
    private lateinit var presenter: EventCaptureFormPresenter
    private val activityPresenter: EventCaptureContract.Presenter = mock()
    private val view: EventCaptureFormView = mock()
    private val schedulerProvider = TrampolineSchedulerProvider()
    private val onRowActionProcessor: FlowableProcessor<RowAction> = mock()
    private val formRepository: FormRepository = mock()

    @Before
    fun setUp() {
        presenter = EventCaptureFormPresenter(
            view,
            activityPresenter,
            schedulerProvider,
            onRowActionProcessor,
            formRepository
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
        val action = RowAction("testUid", "testValue", type = ActionType.ON_SAVE)
        whenever(onRowActionProcessor.onBackpressureBuffer()) doReturn mock()
        whenever(
            onRowActionProcessor.onBackpressureBuffer().distinctUntilChanged()
        ) doReturn Flowable.just(action)
        whenever(activityPresenter.formFieldsFlowable()) doReturn BehaviorSubject.create()
        presenter.init()

        verify(formRepository, times(1)).processUserAction(action)
    }

    @Test
    fun `Should ask for new calculation if value saved changed`() {
        val action = RowAction("testUid", "testValue", type = ActionType.ON_SAVE)

        whenever(onRowActionProcessor.onBackpressureBuffer()) doReturn mock()
        whenever(
            onRowActionProcessor.onBackpressureBuffer().distinctUntilChanged()
        ) doReturn Flowable.just(action)
        whenever(activityPresenter.formFieldsFlowable()) doReturn BehaviorSubject.create()
        whenever(
            formRepository.processUserAction(action)
        ) doReturn StoreResult("testUid", ValueStoreResult.VALUE_CHANGED)

        presenter.init()

        verify(activityPresenter, times(1)).nextCalculation(true)
    }

    @Test
    fun `Should not ask for new calculation if value saved did not changed`() {
        val action = RowAction("testUid", "testValue", type = ActionType.ON_SAVE)

        val subject = BehaviorSubject.create<List<FieldUiModel>>()
        whenever(onRowActionProcessor.onBackpressureBuffer()) doReturn mock()
        whenever(
            onRowActionProcessor.onBackpressureBuffer().distinctUntilChanged()
        ) doReturn Flowable.just(action)
        whenever(activityPresenter.formFieldsFlowable()) doReturn subject
        whenever(
            formRepository.processUserAction(action)
        ) doReturn StoreResult("testUid", ValueStoreResult.VALUE_HAS_NOT_CHANGED)
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
