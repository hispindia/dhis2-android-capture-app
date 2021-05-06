package org.dhis2afgamis.usescases.notes.noteDetail

import dagger.Subcomponent
import org.dhis2afgamis.data.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [NoteDetailModule::class])
interface NoteDetailComponent {
    fun inject(noteDetailActivity: NoteDetailActivity)
}
