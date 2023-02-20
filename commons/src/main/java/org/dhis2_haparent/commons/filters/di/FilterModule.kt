package org.dhis2_haparent.commons.filters.di

import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import org.dhis2_haparent.commons.filters.FilterManager
import org.dhis2_haparent.commons.filters.FilterResources
import org.dhis2_haparent.commons.filters.workingLists.EventFilterToWorkingListItemMapper
import org.dhis2_haparent.commons.filters.workingLists.TeiFilterToWorkingListItemMapper
import org.dhis2_haparent.commons.resources.ResourceManager

@Module
class FilterModule {
    @Provides
    @Singleton
    fun filterManager(resourceManager: ResourceManager): FilterManager {
        return FilterManager.initWith(resourceManager)
    }

    @Provides
    @Singleton
    fun eventWorkingListMapper(
        resourceManager: FilterResources
    ): EventFilterToWorkingListItemMapper {
        return EventFilterToWorkingListItemMapper(
            resourceManager.defaultWorkingListLabel()
        )
    }

    @Provides
    @Singleton
    fun teiWorkingListMapper(resourceManager: FilterResources): TeiFilterToWorkingListItemMapper {
        return TeiFilterToWorkingListItemMapper(
            resourceManager.defaultWorkingListLabel()
        )
    }

    @Provides
    @Singleton
    fun provideFilterResources(resourceManager: ResourceManager): FilterResources {
        return FilterResources(resourceManager)
    }
}
