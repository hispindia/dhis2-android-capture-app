package org.dhis2_haparent.usescases.searchTrackEntity.listView

import dagger.Module
import dagger.Subcomponent
import org.dhis2_haparent.commons.di.dagger.PerFragment

@PerFragment
@Subcomponent(modules = [SearchTEListModule::class])
interface SearchTEListComponent {
    fun inject(fragment: SearchTEList)
}

@Module
class SearchTEListModule()
