package org.fpandhis2.usescases.eventsWithoutRegistration.eventSummary;

import org.fpandhis2.data.dagger.PerActivity;

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