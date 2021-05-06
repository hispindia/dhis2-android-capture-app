package org.dhis2afgamis.data.service;

import androidx.annotation.NonNull;

import org.dhis2afgamis.data.dagger.PerService;

import dagger.Subcomponent;

@PerService
@Subcomponent(modules = ReservedValuesWorkerModule.class)
public interface ReservedValuesWorkerComponent {
    void inject(@NonNull ReservedValuesWorker reservedValuesWorker);
}
