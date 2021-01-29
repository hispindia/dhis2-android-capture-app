package org.dhis2hiv.usescases.reservedValue;

import org.dhis2hiv.data.dagger.PerActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = ReservedValueModule.class)
public interface ReservedValueComponent {
    void inject(ReservedValueActivity activity);
}
