package org.dhis2_haparent.usescases.searchTrackEntity;

import org.dhis2_haparent.commons.di.dagger.PerActivity;
import org.dhis2_haparent.usescases.searchTrackEntity.listView.SearchTEListComponent;
import org.dhis2_haparent.usescases.searchTrackEntity.listView.SearchTEListModule;
import org.dhis2_haparent.usescases.searchTrackEntity.mapView.SearchTEMapComponent;
import org.dhis2_haparent.usescases.searchTrackEntity.mapView.SearchTEMapModule;

import dagger.Subcomponent;

/**
 * QUADRAM. Created by ppajuelo on 31/10/2017.
 */

@PerActivity
@Subcomponent(modules = SearchTEModule.class)
public interface SearchTEComponent {
    void inject(SearchTEActivity activity);

    SearchTEListComponent plus(SearchTEListModule searchTEListModule);
    SearchTEMapComponent plus(SearchTEMapModule searchTEListModule);
}