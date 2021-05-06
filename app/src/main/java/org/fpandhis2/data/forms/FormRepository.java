package org.fpandhis2.data.forms;

import androidx.annotation.NonNull;

import org.hisp.dhis.rules.RuleEngine;

import io.reactivex.Flowable;

public interface FormRepository {

    Flowable<RuleEngine> restartRuleEngine();

    @NonNull
    Flowable<RuleEngine> ruleEngine();

}