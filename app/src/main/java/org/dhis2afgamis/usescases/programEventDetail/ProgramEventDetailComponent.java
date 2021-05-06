package org.dhis2afgamis.usescases.programEventDetail;

import org.dhis2afgamis.data.dagger.PerActivity;

import dagger.Subcomponent;

/**
 * Created by Cristian on 13/02/2018.
 *
 */

@PerActivity
@Subcomponent(modules = ProgramEventDetailModule.class)
public interface ProgramEventDetailComponent {
    void inject(ProgramEventDetailActivity activity);
}