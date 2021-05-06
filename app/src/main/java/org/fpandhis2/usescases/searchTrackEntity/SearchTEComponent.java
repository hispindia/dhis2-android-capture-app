package org.fpandhis2.usescases.searchTrackEntity;

import org.fpandhis2.data.dagger.PerActivity;

import dagger.Subcomponent;

/**
 * QUADRAM. Created by ppajuelo on 31/10/2017.
 */

@PerActivity
@Subcomponent(modules = SearchTEModule.class)
public interface SearchTEComponent {
    void inject(SearchTEActivity activity);
}