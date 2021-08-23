package org.medhis2yes.usescases.eventsWithoutRegistration.eventCapture.eventCaptureFragment

import dagger.Module
import dagger.Provides
import io.reactivex.processors.FlowableProcessor
import org.medhis2yes.data.dagger.PerFragment
import org.medhis2yes.data.schedulers.SchedulerProvider
import org.medhis2yes.form.data.FormRepository
import org.medhis2yes.form.model.RowAction
import org.medhis2yes.usescases.eventsWithoutRegistration.eventCapture.EventCaptureContract

@PerFragment
@Module
class EventCaptureFormModule(
    val view: EventCaptureFormView,
    val eventUid: String
) {

    @Provides
    @PerFragment
    fun providePresenter(
        activityPresenter: EventCaptureContract.Presenter,
        schedulerProvider: SchedulerProvider,
        onFieldActionProcessor: FlowableProcessor<RowAction>,
        formRepository: FormRepository
    ): EventCaptureFormPresenter {
        return EventCaptureFormPresenter(
            view,
            activityPresenter,
            schedulerProvider,
            onFieldActionProcessor,
            formRepository
        )
    }
}
