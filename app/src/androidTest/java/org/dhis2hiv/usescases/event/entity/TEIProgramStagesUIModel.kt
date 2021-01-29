package org.dhis2hiv.usescases.event.entity

data class TEIProgramStagesUIModel (
    val programStage_first: ProgramStageUIModel,
    val programStage_second: ProgramStageUIModel,
    val programStage_third: ProgramStageUIModel
)

data class ProgramStageUIModel (
    val name: String,
    val events: String
)