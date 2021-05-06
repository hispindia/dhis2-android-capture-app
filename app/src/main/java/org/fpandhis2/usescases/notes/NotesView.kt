package org.fpandhis2.usescases.notes

import org.fpandhis2.usescases.general.AbstractActivityContracts
import org.hisp.dhis.android.core.note.Note

interface NotesView : AbstractActivityContracts.View {

    fun swapNotes(notes: List<Note>)

    fun setWritePermission(writePermission: Boolean)

    fun setEmptyNotes()
}
