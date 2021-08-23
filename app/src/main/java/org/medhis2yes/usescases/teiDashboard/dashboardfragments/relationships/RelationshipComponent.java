package org.medhis2yes.usescases.teiDashboard.dashboardfragments.relationships;

import org.medhis2yes.data.dagger.PerFragment;

import dagger.Subcomponent;

/**
 * QUADRAM. Created by ppajuelo on 09/04/2019.
 */
@PerFragment
@Subcomponent(modules = RelationshipModule.class)
public interface RelationshipComponent {

    void inject(RelationshipFragment relationshipFragment);

}
