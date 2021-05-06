package org.fpandhis2.usescases.eventsWithoutRegistration.eventInitial;

import org.fpandhis2.data.dagger.PerActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = EventInitialModule.class)
public interface EventInitialComponent {
    void inject(EventInitialActivity activity);
}