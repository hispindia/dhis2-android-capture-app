package org.medhis2yes.data.forms.dataentry;

import org.medhis2yes.utils.Result;
import org.hisp.dhis.rules.RuleEngine;
import org.hisp.dhis.rules.models.RuleEffect;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;

public interface RuleEngineRepository {

    Flowable<RuleEngine> updateRuleEngine();

    @NonNull
    Flowable<Result<RuleEffect>> calculate();

    @NonNull
    Flowable<Result<RuleEffect>> reCalculate();

}
