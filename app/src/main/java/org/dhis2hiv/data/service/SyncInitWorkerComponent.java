package org.dhis2hiv.data.service;

import androidx.annotation.NonNull;

import org.dhis2hiv.data.dagger.PerService;

import dagger.Subcomponent;

/**
 * QUADRAM. Created by ppajuelo on 24/10/2018.
 */
@PerService
@Subcomponent(modules = SyncInitWorkerModule.class)
public interface SyncInitWorkerComponent {
    void inject(@NonNull SyncInitWorker syncInitWorker);
}
