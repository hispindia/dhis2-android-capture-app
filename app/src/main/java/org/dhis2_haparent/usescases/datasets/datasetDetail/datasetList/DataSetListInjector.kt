package org.dhis2_haparent.usescases.datasets.datasetDetail.datasetList

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import org.dhis2_haparent.commons.di.dagger.PerFragment
import org.dhis2_haparent.commons.filters.FilterManager
import org.dhis2_haparent.commons.matomo.MatomoAnalyticsController
import org.dhis2_haparent.commons.schedulers.SchedulerProvider
import org.dhis2_haparent.usescases.datasets.datasetDetail.DataSetDetailRepository

@PerFragment
@Subcomponent(modules = [DataSetListModule::class])
interface DataSetListComponent {
    fun inject(fragment: DataSetListFragment)
}

@Module
class DataSetListModule {
    @Provides
    @PerFragment
    fun provideViewModelFactory(
        dataSetDetailRepository: DataSetDetailRepository,
        schedulerProvider: SchedulerProvider,
        filterManager: FilterManager,
        matomoAnalyticsController: MatomoAnalyticsController
    ): DataSetListViewModelFactory =
        DataSetListViewModelFactory(
            dataSetDetailRepository,
            schedulerProvider,
            filterManager,
            matomoAnalyticsController
        )
}
