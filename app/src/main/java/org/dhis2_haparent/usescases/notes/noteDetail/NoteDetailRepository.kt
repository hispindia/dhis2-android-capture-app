package org.dhis2_haparent.usescases.notes.noteDetail

import io.reactivex.Single
import org.dhis2_haparent.usescases.notes.NoteType
import org.hisp.dhis.android.core.note.Note

interface NoteDetailRepository {
    fun getNote(noteId: String): Single<Note>
    fun saveNote(type: NoteType, uid: String, message: String): Single<String>
}
