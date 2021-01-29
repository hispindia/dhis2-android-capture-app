package org.dhis2hiv.usescases.eventsWithoutRegistration.eventSummary;

import org.dhis2hiv.data.dagger.PerActivity;

import dagger.Subcomponent;

/**
 * Created by Cristian on 13/02/2018.
 *
 */

@PerActivity
@Subcomponent(modules = EventSummaryModule.class)
public interface EventSummaryComponent {
    void inject(EventSummaryActivity activity);
}