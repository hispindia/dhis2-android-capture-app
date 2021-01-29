package org.dhis2hiv.data.service;

import androidx.annotation.NonNull;

import org.dhis2hiv.data.dagger.PerService;

import dagger.Subcomponent;

/**
 * QUADRAM. Created by ppajuelo on 24/10/2018.
 */
@PerService
@Subcomponent(modules = SyncMetadataWorkerModule.class)
public interface SyncMetadataWorkerComponent {
    void inject(@NonNull SyncMetadataWorker syncMetadataWorker);
}
