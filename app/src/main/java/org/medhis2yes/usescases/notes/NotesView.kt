package org.medhis2yes.usescases.notes

import org.medhis2yes.usescases.general.AbstractActivityContracts
import org.hisp.dhis.android.core.note.Note

interface NotesView : AbstractActivityContracts.View {

    fun swapNotes(notes: List<Note>)

    fun setWritePermission(writePermission: Boolean)

    fun setEmptyNotes()
}
