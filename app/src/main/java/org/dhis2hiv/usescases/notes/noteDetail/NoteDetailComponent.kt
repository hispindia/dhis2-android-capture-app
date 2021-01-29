package org.dhis2hiv.usescases.notes.noteDetail

import dagger.Subcomponent
import org.dhis2hiv.data.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [NoteDetailModule::class])
interface NoteDetailComponent {
    fun inject(noteDetailActivity: NoteDetailActivity)
}
