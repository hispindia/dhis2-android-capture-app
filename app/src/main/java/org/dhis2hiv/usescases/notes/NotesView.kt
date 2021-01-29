package org.dhis2hiv.usescases.notes

import org.dhis2hiv.usescases.general.AbstractActivityContracts
import org.hisp.dhis.android.core.note.Note

interface NotesView : AbstractActivityContracts.View {

    fun swapNotes(notes: List<Note>)

    fun setWritePermission(writePermission: Boolean)

    fun setEmptyNotes()
}
