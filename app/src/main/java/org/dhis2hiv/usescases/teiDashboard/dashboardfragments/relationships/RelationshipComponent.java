package org.dhis2hiv.usescases.teiDashboard.dashboardfragments.relationships;

import org.dhis2hiv.data.dagger.PerFragment;

import dagger.Subcomponent;

/**
 * QUADRAM. Created by ppajuelo on 09/04/2019.
 */
@PerFragment
@Subcomponent(modules = RelationshipModule.class)
public interface RelationshipComponent {

    void inject(RelationshipFragment relationshipFragment);

}
