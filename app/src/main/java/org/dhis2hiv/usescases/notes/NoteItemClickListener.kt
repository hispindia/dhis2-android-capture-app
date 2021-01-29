package org.dhis2hiv.usescases.notes

import android.view.View
import org.hisp.dhis.android.core.note.Note

interface NoteItemClickListener {
    fun onNoteClick(view: View, note: Note)
}
