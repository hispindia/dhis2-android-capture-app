package org.dhis2afgamis.usescases.notes.noteDetail

import dagger.Module
import dagger.Provides
import org.dhis2afgamis.data.dagger.PerActivity
import org.dhis2afgamis.data.schedulers.SchedulerProvider
import org.hisp.dhis.android.core.D2

@Module
class NoteDetailModule(
    val view: NoteDetailView,
    private val noteId: String? = null,
    private val programUid: String
) {

    @Provides
    @PerActivity
    fun providesRepository(d2: D2): NoteDetailRepository {
        return NoteDetailRepositoryImpl(d2, programUid)
    }

    @Provides
    @PerActivity
    fun providesPresenter(
        repository: NoteDetailRepository,
        schedulerProvider: SchedulerProvider
    ): NoteDetailPresenter {
        return NoteDetailPresenter(view, schedulerProvider, noteId, repository)
    }
}
