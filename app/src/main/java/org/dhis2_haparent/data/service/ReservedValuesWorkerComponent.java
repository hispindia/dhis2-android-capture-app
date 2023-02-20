package org.dhis2_haparent.data.service;

import androidx.annotation.NonNull;

import org.dhis2_haparent.commons.di.dagger.PerService;

import dagger.Subcomponent;

@PerService
@Subcomponent(modules = ReservedValuesWorkerModule.class)
public interface ReservedValuesWorkerComponent {
    void inject(@NonNull ReservedValuesWorker reservedValuesWorker);
}
