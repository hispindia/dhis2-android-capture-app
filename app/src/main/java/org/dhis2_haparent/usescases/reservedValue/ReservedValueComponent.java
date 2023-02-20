package org.dhis2_haparent.usescases.reservedValue;

import org.dhis2_haparent.commons.di.dagger.PerActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = ReservedValueModule.class)
public interface ReservedValueComponent {
    void inject(ReservedValueActivity activity);
}
