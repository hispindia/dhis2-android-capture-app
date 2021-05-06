package org.fpandhis2.usescases.notes.noteDetail

import org.fpandhis2.data.tuples.Trio
import org.fpandhis2.usescases.notes.NoteType
import org.hisp.dhis.android.core.note.Note

interface NoteDetailView {
    fun showDiscardDialog()
    fun setNote(note: Note)
    fun getNewNote(): Trio<NoteType, String, String>
    fun noteSaved()
    fun back()
}
