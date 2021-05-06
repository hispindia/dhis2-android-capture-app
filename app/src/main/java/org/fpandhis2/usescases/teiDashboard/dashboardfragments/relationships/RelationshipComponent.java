package org.fpandhis2.usescases.teiDashboard.dashboardfragments.relationships;

import org.fpandhis2.data.dagger.PerFragment;

import dagger.Subcomponent;

/**
 * QUADRAM. Created by ppajuelo on 09/04/2019.
 */
@PerFragment
@Subcomponent(modules = RelationshipModule.class)
public interface RelationshipComponent {

    void inject(RelationshipFragment relationshipFragment);

}
