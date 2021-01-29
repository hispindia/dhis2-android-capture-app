package org.dhis2hiv.usescases.main.program

import io.reactivex.Flowable

internal interface HomeRepository {

    fun programModels(): Flowable<List<ProgramViewModel>>

    fun aggregatesModels(): Flowable<List<ProgramViewModel>>
}
