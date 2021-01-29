package org.dhis2hiv.usescases.programEventDetail;

import org.dhis2hiv.data.dagger.PerActivity;

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