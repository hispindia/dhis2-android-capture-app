package org.medhis2yes.data.service;

import androidx.annotation.NonNull;

import org.medhis2yes.data.dagger.PerService;

import dagger.Subcomponent;

/**
 * QUADRAM. Created by ppajuelo on 24/10/2018.
 */
@PerService
@Subcomponent(modules = SyncMetadataWorkerModule.class)
public interface SyncMetadataWorkerComponent {
    void inject(@NonNull SyncMetadataWorker syncMetadataWorker);
}
