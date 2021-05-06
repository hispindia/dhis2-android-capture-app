package org.dhis2afgamis.usescases.eventsWithoutRegistration.eventInitial;

import org.dhis2afgamis.data.dagger.PerActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = EventInitialModule.class)
public interface EventInitialComponent {
    void inject(EventInitialActivity activity);
}