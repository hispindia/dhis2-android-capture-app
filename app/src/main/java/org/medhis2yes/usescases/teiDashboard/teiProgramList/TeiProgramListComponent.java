package org.medhis2yes.usescases.teiDashboard.teiProgramList;

import org.medhis2yes.data.dagger.PerActivity;

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