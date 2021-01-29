package org.dhis2hiv.usescases.searchTrackEntity;

import org.dhis2hiv.data.dagger.PerActivity;

import dagger.Subcomponent;

/**
 * QUADRAM. Created by ppajuelo on 31/10/2017.
 */

@PerActivity
@Subcomponent(modules = SearchTEModule.class)
public interface SearchTEComponent {
    void inject(SearchTEActivity activity);
}