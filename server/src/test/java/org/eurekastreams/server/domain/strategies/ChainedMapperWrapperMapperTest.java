/*
 * Copyright (c) 2010-2011 Lockheed Martin Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.eurekastreams.server.domain.strategies;

import static org.junit.Assert.assertSame;

import java.io.Serializable;

import org.eurekastreams.server.persistence.mappers.DomainMapper;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;

/**
 * Test fixture for ChainedMapperWrapperMapper.
 */
public class ChainedMapperWrapperMapperTest
{
    /**
     * Context for building mock objects.
     */
    private final Mockery context = new JUnit4Mockery()
    {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    /**
     * First mapper.
     */
    private DomainMapper<Serializable, Serializable> mapper1 = context.mock(DomainMapper.class, "mapper1");

    /**
     * Second mapper.
     */
    private DomainMapper<Serializable, Serializable> mapper2 = context.mock(DomainMapper.class, "mapper2");

    /**
     * System under test.
     */
    private ChainedMapperWrapperMapper sut = new ChainedMapperWrapperMapper(mapper1, mapper2);

    /**
     * Test execute().
     */
    @Test
    public void testExecute()
    {
        final String request = "a";
        final String response1 = "b";
        final String response2 = "c";

        context.checking(new Expectations()
        {
            {
                oneOf(mapper1).execute(request);
                will(returnValue(response1));

                oneOf(mapper2).execute(response1);
                will(returnValue(response2));
            }
        });

        assertSame(response2, sut.execute(request));
        context.assertIsSatisfied();
    }
}
