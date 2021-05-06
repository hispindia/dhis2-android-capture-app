package org.fpandhis2.usescases.teiDashboard;

import org.fpandhis2.data.dagger.PerActivity;
import org.fpandhis2.usescases.teiDashboard.dashboardfragments.indicators.IndicatorsComponent;
import org.fpandhis2.usescases.teiDashboard.dashboardfragments.indicators.IndicatorsModule;
import org.fpandhis2.usescases.notes.NotesComponent;
import org.fpandhis2.usescases.notes.NotesModule;
import org.fpandhis2.usescases.teiDashboard.dashboardfragments.relationships.RelationshipComponent;
import org.fpandhis2.usescases.teiDashboard.dashboardfragments.relationships.RelationshipModule;
import org.fpandhis2.usescases.teiDashboard.dashboardfragments.teidata.TEIDataComponent;
import org.fpandhis2.usescases.teiDashboard.dashboardfragments.teidata.TEIDataModule;

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
