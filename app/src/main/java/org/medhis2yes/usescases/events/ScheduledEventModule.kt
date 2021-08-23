package org.medhis2yes.usescases.events

import dagger.Module
import dagger.Provides
import org.medhis2yes.data.dagger.PerActivity
import org.medhis2yes.data.dhislogic.DhisEventUtils
import org.hisp.dhis.android.core.D2

@Module
@PerActivity
class ScheduledEventModule(val eventUid: String, val view: ScheduledEventContract.View) {

    @Provides
    @PerActivity
    internal fun providePresenter(
        d2: D2,
        eventUtils: DhisEventUtils
    ): ScheduledEventContract.Presenter {
        return ScheduledEventPresenterImpl(view, d2, eventUid, eventUtils)
    }
}
