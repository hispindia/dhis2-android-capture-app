package org.medhis2yes.usescases.notes.noteDetail

import org.medhis2yes.data.tuples.Trio
import org.medhis2yes.usescases.notes.NoteType
import org.hisp.dhis.android.core.note.Note

interface NoteDetailView {
    fun showDiscardDialog()
    fun setNote(note: Note)
    fun getNewNote(): Trio<NoteType, String, String>
    fun noteSaved()
    fun back()
}
