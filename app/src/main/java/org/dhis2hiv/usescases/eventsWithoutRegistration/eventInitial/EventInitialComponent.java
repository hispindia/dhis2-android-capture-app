package org.dhis2hiv.usescases.eventsWithoutRegistration.eventInitial;

import org.dhis2hiv.data.dagger.PerActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = EventInitialModule.class)
public interface EventInitialComponent {
    void inject(EventInitialActivity activity);
}