package org.fpandhis2.usescases.programStageSelection;

import org.fpandhis2.data.dagger.PerActivity;

import dagger.Subcomponent;

/**
 * Created by ppajuelo on 30/11/2017.
 *
 */
@PerActivity
@Subcomponent(modules = ProgramStageSelectionModule.class)
public interface ProgramStageSelectionComponent {
    void inject(ProgramStageSelectionActivity programStageSelectionActivity);
}
