/*
 * Copyright (c) 2009-2011 Lockheed Martin Corporation
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
package org.eurekastreams.server.persistence.mappers.ldap.callback;

import java.util.Collections;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for mapping LDAP record to Person.
 */
public class LdapToPersonMapperTest
{
    /**
     * Mocking context.
     */
    private final JUnit4Mockery context = new JUnit4Mockery()
    {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    /**
     * System under test.
     */
    private LdapToPersonMapper sut;

    /**
     * Setup fixtures.
     */
    @Before
    public final void setUp()
    {
        sut = new LdapToPersonMapper();
        sut.setAccountAttrib("sAMAccountName");
        sut.setFirstNameAttrib("givenName");
        sut.setFullNameAttrib("cn");
        sut.setLastNameAttrib("sn");
        sut.setMiddleNameAttrib("middleName");
        sut.setTitleAttrib("title");
        sut.setEmailAttrib("mail");
        sut.setCompanyAttrib("companyName");
    }

    /**
     * Tests mapping a user.
     * 
     * @throws NamingException
     *             shouldn't happen.
     */
    @Test
    public final void testMapper() throws NamingException
    {
        final Attributes attributeMock = context.mock(Attributes.class);

        final Attribute cnMock = context.mock(Attribute.class, "cn");
        final Attribute sAMAccountNameMock = context.mock(Attribute.class, "sAMAccountName");
        final Attribute givenNameMock = context.mock(Attribute.class, "givenName");
        final Attribute snMock = context.mock(Attribute.class, "sn");
        final Attribute middleNameMock = context.mock(Attribute.class, "middleName");
        final Attribute titleMock = context.mock(Attribute.class, "title");
        final Attribute emailMock = context.mock(Attribute.class, "email");
        final Attribute propMock = context.mock(Attribute.class, "someKey");
        final Attribute companyMock = context.mock(Attribute.class, "company");

        List<String> props = Collections.singletonList("someKey");
        sut.setAdditionalProperties(props);

        context.checking(new Expectations()
        {
            {
                exactly(2).of(cnMock).get().toString();
                will(returnValue("First Last"));

                exactly(2).of(attributeMock).get("cn");
                will(returnValue(cnMock));

                oneOf(attributeMock).get("sAMAccountName");
                will(returnValue(sAMAccountNameMock));

                oneOf(sAMAccountNameMock).get().toString();
                will(returnValue("lastf"));

                oneOf(attributeMock).get("givenName");
                will(returnValue(givenNameMock));

                exactly(2).of(attributeMock).get("middleName");
                will(returnValue(middleNameMock));

                exactly(2).of(attributeMock).get("companyName");
                will(returnValue(companyMock));

                exactly(2).of(attributeMock).get("title");
                will(returnValue(titleMock));

                exactly(2).of(attributeMock).get("mail");
                will(returnValue(emailMock));

                exactly(1).of(attributeMock).get("someKey");
                will(returnValue(propMock));

                oneOf(givenNameMock).get().toString();
                will(returnValue("First"));

                oneOf(titleMock).get().toString();
                will(returnValue("my title"));

                oneOf(middleNameMock).get().toString();
                will(returnValue("Middle"));

                oneOf(companyMock).get().toString();
                will(returnValue("company"));

                oneOf(attributeMock).get("sn");
                will(returnValue(snMock));

                oneOf(snMock).get().toString();
                will(returnValue("Last"));

                oneOf(emailMock).get().toString();
                will(returnValue("something@someorg.com"));

                oneOf(propMock).get().toString();
                will(returnValue("someValue"));
            }
        });

        sut.mapFromAttributes(attributeMock);

        context.assertIsSatisfied();
    }

    /**
     * Tests mapping a users without optional values.
     * 
     * @throws NamingException
     *             shoulnd't be thrown here.
     */
    @Test
    public final void testAttribMapperWithoutOptionals() throws NamingException
    {
        final Attributes attributeMock = context.mock(Attributes.class);

        final Attribute cnMock = context.mock(Attribute.class, "cn");
        final Attribute sAMAccountNameMock = context.mock(Attribute.class, "sAMAccountName");
        final Attribute givenNameMock = context.mock(Attribute.class, "givenName");
        final Attribute snMock = context.mock(Attribute.class, "sn");
        final Attribute middleNameMock = null;
        final Attribute titleMock = null;
        final Attribute companyMock = null;

        context.checking(new Expectations()
        {
            {
                exactly(2).of(cnMock).get().toString();
                will(returnValue("Last, First"));

                exactly(2).of(attributeMock).get("cn");
                will(returnValue(cnMock));

                oneOf(attributeMock).get("sAMAccountName");
                will(returnValue(sAMAccountNameMock));

                oneOf(sAMAccountNameMock).get().toString();
                will(returnValue("lastf"));

                oneOf(attributeMock).get("givenName");
                will(returnValue(givenNameMock));

                oneOf(attributeMock).get("companyName");
                will(returnValue(companyMock));

                oneOf(attributeMock).get("middleName");
                will(returnValue(middleNameMock));

                oneOf(attributeMock).get("title");
                will(returnValue(titleMock));

                oneOf(givenNameMock).get().toString();
                will(returnValue("First"));

                exactly(1).of(attributeMock).get("mail");
                will(returnValue(null));

                oneOf(attributeMock).get("sn");
                will(returnValue(snMock));

                oneOf(snMock).get().toString();
                will(returnValue("Last"));
            }
        });

        sut.mapFromAttributes(attributeMock);
        context.assertIsSatisfied();
    }

    /**
     * Tests mapping a null user..
     * 
     * @throws NamingException
     *             shoulnd't be thrown here.
     */
    @Test
    public final void testAttribMapperNulls() throws NamingException
    {
        final Attributes attributeMock = context.mock(Attributes.class);

        context.checking(new Expectations()
        {
            {
                oneOf(attributeMock).get("cn");
                will(returnValue(null));
            }
        });

        sut.mapFromAttributes(attributeMock);

        context.assertIsSatisfied();
    }
}
