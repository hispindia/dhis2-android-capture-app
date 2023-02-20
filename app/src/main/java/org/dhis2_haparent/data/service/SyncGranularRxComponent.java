package org.dhis2_haparent.data.service;

import androidx.annotation.NonNull;

import org.dhis2_haparent.commons.di.dagger.PerService;

import dagger.Subcomponent;

@PerService
@Subcomponent(modules = SyncGranularRxModule.class)
public interface SyncGranularRxComponent {
    void inject(@NonNull SyncGranularWorker syncGranularWorker);
}
