package org.dhis2_haparent.usescases.searchTrackEntity.mapView

import dagger.Module
import dagger.Subcomponent
import org.dhis2_haparent.commons.di.dagger.PerFragment

@PerFragment
@Subcomponent(modules = [SearchTEMapModule::class])
interface SearchTEMapComponent {
    fun inject(fragment: SearchTEMap)
}

@Module
class SearchTEMapModule()
