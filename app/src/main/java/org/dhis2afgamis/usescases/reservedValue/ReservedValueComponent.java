package org.dhis2afgamis.usescases.reservedValue;

import org.dhis2afgamis.data.dagger.PerActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = ReservedValueModule.class)
public interface ReservedValueComponent {
    void inject(ReservedValueActivity activity);
}
