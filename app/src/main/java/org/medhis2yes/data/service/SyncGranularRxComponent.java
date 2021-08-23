package org.medhis2yes.data.service;

import androidx.annotation.NonNull;

import org.medhis2yes.data.dagger.PerService;

import dagger.Subcomponent;

@PerService
@Subcomponent(modules = SyncGranularRxModule.class)
public interface SyncGranularRxComponent {
    void inject(@NonNull SyncGranularWorker syncGranularWorker);
}
