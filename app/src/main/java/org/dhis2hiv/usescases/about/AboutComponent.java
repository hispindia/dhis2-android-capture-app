package org.dhis2hiv.usescases.about;

import org.dhis2hiv.data.dagger.PerFragment;

import dagger.Subcomponent;

/**
 * QUADRAM. Created by ppajuelo on 05/07/2018.
 */
@PerFragment
@Subcomponent(modules = AboutModule.class)
public interface AboutComponent {
    void inject(AboutFragment programFragment);

}
