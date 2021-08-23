package org.medhis2yes.usescases.eventsWithoutRegistration.eventInitial;

import org.medhis2yes.data.dagger.PerActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = EventInitialModule.class)
public interface EventInitialComponent {
    void inject(EventInitialActivity activity);
}