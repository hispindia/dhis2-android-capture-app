package org.hisp.dhis.rules.functions;

/*
 * Copyright (c) 2004-2018, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import org.hisp.dhis.rules.RuleVariableValue;
import org.hisp.dhis.rules.RuleVariableValueBuilder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author Zubair Asghar.
 */
public class RuleFunctionLastEventDateTests
{
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Map<String, RuleVariableValue> valueMap = new HashMap<>();

    private SimpleDateFormat dateFormat = new SimpleDateFormat( DATE_PATTERN, Locale.US );

    private String todayDate = dateFormat.format( new Date() );

    @Test
    public void throwExceptionIfArgumentListIsZero()
    {
        RuleFunction lastEventDateFunction = RuleFunctionLastEventDate.create();

        thrown.expect( IllegalArgumentException.class );

        lastEventDateFunction.evaluate( Arrays.asList(), valueMap, null);
    }

    @Test
    public void returnNothingWhenValueMapDoesNotHaveValue()
    {
        RuleFunction lastEventDateFunction = RuleFunctionLastEventDate.create();

        valueMap = getEmptyValueMap();

        assertThat( lastEventDateFunction.evaluate( Arrays.asList( "test_variable" ), valueMap, null ), is( "" ) );
    }

    @Test
    public void returnLastestDateWhenValueExist()
    {
        RuleFunction lastEventDateFunction = RuleFunctionLastEventDate.create();

        String variableWithValue = "test_variable_one";

        valueMap = getValueMapWithValue( variableWithValue );

        assertThat( lastEventDateFunction.evaluate( Arrays.asList( variableWithValue ), valueMap, null ), is( "'"+todayDate+"'" ) );
    }

    private Map<String, RuleVariableValue> getEmptyValueMap()
    {
        return new HashMap<>();
    }

    private Map<String, RuleVariableValue> getValueMapWithValue( String variableNameWithValue )
    {
        valueMap.put( variableNameWithValue, RuleVariableValueBuilder
                .create()
                .withValue( "value" )
                .withCandidates( Arrays.asList() )
                .withEventDate( todayDate ).build() );

        return valueMap;
    }
}
