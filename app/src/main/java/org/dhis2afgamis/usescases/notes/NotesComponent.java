package org.dhis2afgamis.usescases.notes;

import org.dhis2afgamis.data.dagger.PerFragment;

import dagger.Subcomponent;

/**
 * QUADRAM. Created by ppajuelo on 09/04/2019.
 */
@PerFragment
@Subcomponent(modules = NotesModule.class)
public interface NotesComponent {

    void inject(NotesFragment notesFragment);

}
