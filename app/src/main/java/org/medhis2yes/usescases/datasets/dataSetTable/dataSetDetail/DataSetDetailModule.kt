package org.medhis2yes.usescases.datasets.dataSetTable.dataSetDetail

import dagger.Module
import dagger.Provides
import org.medhis2yes.data.dagger.PerFragment
import org.medhis2yes.data.schedulers.SchedulerProvider
import org.medhis2yes.usescases.datasets.dataSetTable.DataSetTableRepositoryImpl
import org.medhis2yes.utils.analytics.matomo.MatomoAnalyticsController

@Module
@PerFragment
class DataSetDetailModule(
    private val dataSetDetailView: DataSetDetailView,
    private val dataSetUid: String
) {
    @Provides
    @PerFragment
    fun providePresenter(
        dataSetTableRepository: DataSetTableRepositoryImpl,
        schedulers: SchedulerProvider,
        matomoAnalyticsController: MatomoAnalyticsController
    ): DataSetDetailPresenter {
        return DataSetDetailPresenter(
            dataSetDetailView,
            dataSetTableRepository,
            schedulers,
            matomoAnalyticsController
        )
    }
}
