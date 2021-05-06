package org.fpandhis2.usescases.reservedValue;

import org.fpandhis2.data.dagger.PerActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = ReservedValueModule.class)
public interface ReservedValueComponent {
    void inject(ReservedValueActivity activity);
}
