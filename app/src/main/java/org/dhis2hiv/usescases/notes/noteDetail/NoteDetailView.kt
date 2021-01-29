package org.dhis2hiv.usescases.notes.noteDetail

import org.dhis2hiv.data.tuples.Trio
import org.dhis2hiv.usescases.notes.NoteType
import org.hisp.dhis.android.core.note.Note

interface NoteDetailView {
    fun showDiscardDialog()
    fun setNote(note: Note)
    fun getNewNote(): Trio<NoteType, String, String>
    fun noteSaved()
    fun back()
}
