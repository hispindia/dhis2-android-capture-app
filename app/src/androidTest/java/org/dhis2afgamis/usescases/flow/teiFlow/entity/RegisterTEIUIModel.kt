package org.dhis2afgamis.usescases.flow.teiFlow.entity

data class RegisterTEIUIModel(
    val name: String,
    val lastName: String,
    val firstSpecificDate: DateRegistrationUIModel,
    val enrollmentDate: DateRegistrationUIModel
)

