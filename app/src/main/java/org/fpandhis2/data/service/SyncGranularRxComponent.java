package org.fpandhis2.data.service;

import androidx.annotation.NonNull;

import org.fpandhis2.data.dagger.PerService;

import dagger.Subcomponent;

@PerService
@Subcomponent(modules = SyncGranularRxModule.class)
public interface SyncGranularRxComponent {
    void inject(@NonNull SyncGranularWorker syncGranularWorker);
}
