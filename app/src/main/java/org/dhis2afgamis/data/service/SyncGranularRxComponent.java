package org.dhis2afgamis.data.service;

import androidx.annotation.NonNull;

import org.dhis2afgamis.data.dagger.PerService;

import dagger.Subcomponent;

@PerService
@Subcomponent(modules = SyncGranularRxModule.class)
public interface SyncGranularRxComponent {
    void inject(@NonNull SyncGranularWorker syncGranularWorker);
}
