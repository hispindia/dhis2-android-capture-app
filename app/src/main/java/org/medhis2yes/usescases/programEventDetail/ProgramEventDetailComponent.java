package org.medhis2yes.usescases.programEventDetail;

import org.medhis2yes.data.dagger.PerActivity;
import org.medhis2yes.usescases.programEventDetail.eventList.EventListComponent;
import org.medhis2yes.usescases.programEventDetail.eventList.EventListModule;
import org.medhis2yes.usescases.programEventDetail.eventMap.EventMapComponent;
import org.medhis2yes.usescases.programEventDetail.eventMap.EventMapModule;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = ProgramEventDetailModule.class)
public interface ProgramEventDetailComponent {
    void inject(ProgramEventDetailActivity activity);

    EventListComponent plus(EventListModule eventListModule);

    EventMapComponent plus(EventMapModule eventMapModule);
}