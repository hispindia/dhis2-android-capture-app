package org.dhis2_haparent.usescases.notes.noteDetail

import dagger.Subcomponent
import org.dhis2_haparent.commons.di.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [NoteDetailModule::class])
interface NoteDetailComponent {
    fun inject(noteDetailActivity: NoteDetailActivity)
}
