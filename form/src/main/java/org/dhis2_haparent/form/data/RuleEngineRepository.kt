package org.dhis2_haparent.form.data

import org.hisp.dhis.rules.models.RuleEffect

interface RuleEngineRepository {
    fun calculate(): List<RuleEffect>
}
