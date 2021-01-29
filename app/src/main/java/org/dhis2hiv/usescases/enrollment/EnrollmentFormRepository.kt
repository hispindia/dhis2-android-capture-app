package org.dhis2hiv.usescases.enrollment

import io.reactivex.Flowable
import io.reactivex.Single
import org.dhis2hiv.utils.Result
import org.hisp.dhis.rules.RuleEngine
import org.hisp.dhis.rules.models.RuleEffect

interface EnrollmentFormRepository {

    fun ruleEngine(): Flowable<RuleEngine>
    fun calculate(): Flowable<Result<RuleEffect>>
    fun useFirstStageDuringRegistration(): Single<Pair<String, String>>
    fun autoGenerateEvents(): Single<Boolean>
    fun getOptionsFromGroups(optionGroupUids: ArrayList<String>): List<String>
    fun getProfilePicture(): String
}
