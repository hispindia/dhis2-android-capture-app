package org.fpandhis2.usescases.programEventDetail;

import org.fpandhis2.data.dagger.PerActivity;

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