package org.fpandhis2.usescases.teiDashboard.teiProgramList;

import org.fpandhis2.data.dagger.PerActivity;

import dagger.Subcomponent;

/**
 * Created by Cristian on 13/02/2018.
 *
 */

@PerActivity
@Subcomponent(modules = TeiProgramListModule.class)
public interface TeiProgramListComponent {
    void inject(TeiProgramListActivity activity);
}