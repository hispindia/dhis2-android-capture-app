package org.dhis2hiv.data.service;

import androidx.annotation.NonNull;

import org.dhis2hiv.data.dagger.PerService;

import dagger.Subcomponent;

@PerService
@Subcomponent(modules = ReservedValuesWorkerModule.class)
public interface ReservedValuesWorkerComponent {
    void inject(@NonNull ReservedValuesWorker reservedValuesWorker);
}
