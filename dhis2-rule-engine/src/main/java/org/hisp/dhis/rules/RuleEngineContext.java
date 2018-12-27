package org.hisp.dhis.rules;

import org.hisp.dhis.rules.models.Rule;
import org.hisp.dhis.rules.models.RuleVariable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Collections.checkedMap;
import static java.util.Collections.unmodifiableList;

public final class RuleEngineContext
{

        @Nonnull
        private final RuleExpressionEvaluator expressionEvaluator;

        @Nonnull
        private final List<Rule> rules;

        @Nonnull
        private final List<RuleVariable> ruleVariables;

        @Nonnull
        private final Map<String, List<String>> supplementaryData;

        @Nonnull
        private final Map<String, Map<String, String>> calculatedValueMap;

        RuleEngineContext( @Nonnull RuleExpressionEvaluator expressionEvaluator,
            @Nonnull List<Rule> rules, @Nonnull List<RuleVariable> ruleVariables, Map<String, List<String>> supplementaryData, Map<String, Map<String, String>> calculatedValueMap )
        {
                this.expressionEvaluator = expressionEvaluator;
                this.rules = rules;
                this.ruleVariables = ruleVariables;
                this.supplementaryData = supplementaryData;
                this.calculatedValueMap = calculatedValueMap;
        }

        @Nonnull
        public List<Rule> rules()
        {
                return rules;
        }

        @Nonnull
        public List<RuleVariable> ruleVariables()
        {
                return ruleVariables;
        }

        @Nonnull
        public Map<String, List<String>> supplementaryData()
        {
                return supplementaryData;
        }

        @Nonnull
        public RuleExpressionEvaluator expressionEvaluator()
        {
                return expressionEvaluator;
        }

        @Nonnull
        public Map<String, Map<String, String>> calculatedValueMap()
        {
                return calculatedValueMap;
        }

        @Nonnull
        public RuleEngine.Builder toEngineBuilder()
        {
                return new RuleEngine.Builder( this );
        }

        @Nonnull
        public static Builder builder( @Nonnull RuleExpressionEvaluator evaluator )
        {
                if ( evaluator == null )
                {
                        throw new IllegalArgumentException( "evaluator == null" );
                }

                return new Builder( evaluator );
        }

        public static class Builder
        {

                @Nonnull
                private final RuleExpressionEvaluator evaluator;

                @Nullable
                private List<Rule> rules;

                @Nullable
                private List<RuleVariable> ruleVariables;

                @Nullable
                private Map<String, List<String>> supplementaryData;

                @Nullable
                private  Map<String, Map<String, String>> calculatedValueMap;

                Builder( @Nonnull RuleExpressionEvaluator evaluator )
                {
                        this.evaluator = evaluator;
                }

                @Nonnull
                public Builder rules( @Nonnull List<Rule> rules )
                {
                        if ( rules == null )
                        {
                                throw new IllegalArgumentException( "rules == null" );
                        }

                        this.rules = unmodifiableList( new ArrayList<>( rules ) );
                        return this;
                }

                @Nonnull
                public Builder ruleVariables( @Nonnull List<RuleVariable> ruleVariables )
                {
                        if ( ruleVariables == null )
                        {
                                throw new IllegalArgumentException( "ruleVariables == null" );
                        }

                        this.ruleVariables = unmodifiableList( new ArrayList<>( ruleVariables ) );
                        return this;
                }

                @Nonnull
                public Builder supplementaryData( Map<String, List<String>> supplementaryData )
                {
                        if ( supplementaryData == null )
                        {
                                throw new IllegalArgumentException( "supplementaryData == null" );
                        }
                        this.supplementaryData = supplementaryData;
                        return this;
                }

                @Nonnull
                public Builder calculatedValueMap( Map<String, Map<String, String>> calculatedValueMap )
                {
                        if ( calculatedValueMap == null )
                        {
                                throw new IllegalArgumentException( "calculatedValueMap == null" );
                        }
                        this.calculatedValueMap = calculatedValueMap;
                        return this;
                }

                @Nonnull
                public RuleEngineContext build()
                {
                        if ( rules == null )
                        {
                                rules = unmodifiableList( new ArrayList<Rule>() );
                        }

                        if ( ruleVariables == null )
                        {
                                ruleVariables = unmodifiableList( new ArrayList<RuleVariable>() );
                        }

                        return new RuleEngineContext( evaluator, rules, ruleVariables, supplementaryData, calculatedValueMap );
                }
        }
}
