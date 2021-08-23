package org.medhis2yes.usescases.notes.noteDetail

import dagger.Subcomponent
import org.medhis2yes.data.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [NoteDetailModule::class])
interface NoteDetailComponent {
    fun inject(noteDetailActivity: NoteDetailActivity)
}
