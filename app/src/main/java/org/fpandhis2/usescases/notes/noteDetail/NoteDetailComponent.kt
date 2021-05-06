package org.fpandhis2.usescases.notes.noteDetail

import dagger.Subcomponent
import org.fpandhis2.data.dagger.PerActivity

@PerActivity
@Subcomponent(modules = [NoteDetailModule::class])
interface NoteDetailComponent {
    fun inject(noteDetailActivity: NoteDetailActivity)
}
