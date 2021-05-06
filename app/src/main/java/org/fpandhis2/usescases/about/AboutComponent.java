package org.fpandhis2.usescases.about;

import org.fpandhis2.data.dagger.PerFragment;

import dagger.Subcomponent;

/**
 * QUADRAM. Created by ppajuelo on 05/07/2018.
 */
@PerFragment
@Subcomponent(modules = AboutModule.class)
public interface AboutComponent {
    void inject(AboutFragment programFragment);

}
