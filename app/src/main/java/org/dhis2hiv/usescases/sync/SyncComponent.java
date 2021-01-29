package org.dhis2hiv.usescases.sync;

import org.dhis2hiv.data.dagger.PerActivity;


import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = SyncModule.class)
public interface SyncComponent {
    void inject(SyncActivity syncActivity);
}