package org.fpandhis2.usescases.notes;

import org.fpandhis2.data.dagger.PerFragment;

import dagger.Subcomponent;

/**
 * QUADRAM. Created by ppajuelo on 09/04/2019.
 */
@PerFragment
@Subcomponent(modules = NotesModule.class)
public interface NotesComponent {

    void inject(NotesFragment notesFragment);

}
