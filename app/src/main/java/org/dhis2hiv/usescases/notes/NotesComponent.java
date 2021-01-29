package org.dhis2hiv.usescases.notes;

import org.dhis2hiv.data.dagger.PerFragment;

import dagger.Subcomponent;

/**
 * QUADRAM. Created by ppajuelo on 09/04/2019.
 */
@PerFragment
@Subcomponent(modules = NotesModule.class)
public interface NotesComponent {

    void inject(NotesFragment notesFragment);

}
