package org.medhis2yes.usescases.teiDashboard;

import org.medhis2yes.data.dagger.PerActivity;
import org.medhis2yes.usescases.teiDashboard.dashboardfragments.indicators.IndicatorsComponent;
import org.medhis2yes.usescases.teiDashboard.dashboardfragments.indicators.IndicatorsModule;
import org.medhis2yes.usescases.notes.NotesComponent;
import org.medhis2yes.usescases.notes.NotesModule;
import org.medhis2yes.usescases.teiDashboard.dashboardfragments.relationships.RelationshipComponent;
import org.medhis2yes.usescases.teiDashboard.dashboardfragments.relationships.RelationshipModule;
import org.medhis2yes.usescases.teiDashboard.dashboardfragments.teidata.TEIDataComponent;
import org.medhis2yes.usescases.teiDashboard.dashboardfragments.teidata.TEIDataModule;

import androidx.annotation.NonNull;
import dagger.Subcomponent;

/**
 * Created by ppajuelo on 30/11/2017.
 */
@PerActivity
@Subcomponent(modules = TeiDashboardModule.class)
public interface TeiDashboardComponent {

    @NonNull
    IndicatorsComponent plus(IndicatorsModule indicatorsModule);

    @NonNull
    RelationshipComponent plus(RelationshipModule relationshipModule);

    @NonNull
    NotesComponent plus(NotesModule notesModule);

    @NonNull
    TEIDataComponent plus(TEIDataModule teiDataModule);

    void inject(TeiDashboardMobileActivity mobileActivity);
}
