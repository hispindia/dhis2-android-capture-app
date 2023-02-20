package org.dhis2_haparent.usescases.settings;

import org.dhis2_haparent.commons.di.dagger.PerFragment;

import dagger.Subcomponent;

/**
 * QUADRAM. Created by frodriguez on 4/13/2018.
 */

@PerFragment
@Subcomponent(modules = SyncManagerModule.class)
public interface SyncManagerComponent {
    void inject(SyncManagerFragment syncManagerFragment);
}
