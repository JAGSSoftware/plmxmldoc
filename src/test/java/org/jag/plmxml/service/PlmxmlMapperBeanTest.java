/*
 * Copyright 2017 Jose A. Garcia Sanchez
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions
 * of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE. 
 */

package org.jag.plmxml.service;

import static com.google.common.truth.Truth.assertThat;

import java.util.List;

import org.jag.plmxml.domain.Category;
import org.jag.plmxml.domain.Preference;
import org.jag.plmxml.domain.ProtectionScope;
import org.jag.plmxml.domain.Type;
import org.jag.plmxml.xml.CategoryPlmxml;
import org.jag.plmxml.xml.ContextPlmxml;
import org.jag.plmxml.xml.PreferencePlmxml;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * @author Jose A. Garcia
 */
@RunWith(JUnitParamsRunner.class)
public class PlmxmlMapperBeanTest {
    private PlmxmlMapperBean underTest;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        underTest = new PlmxmlMapperBean();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    @Parameters(value = { "String,STRING", "Integer,INTEGER", "Logical,LOGICAL", })
    public void mapType(final String typeName, final Type type) {
        assertThat(underTest.mapType(typeName)).isEqualTo(type);
    }

    @Test
    @Parameters(value = { "Site,SITE", "Group,GROUP", "Role,ROLE", "User,USER", })
    public void mapProtectionScope(final String name, final ProtectionScope scope) {
        assertThat(underTest.mapProtectionScope(name)).isEqualTo(scope);
    }

    @Test
    public void mapPreference() {
        final Preference preference = underTest.map(createPreferencePlmxml(), "Test case");

        assertThat(preference).isNotNull();
        assertThat(preference.getName()).isEqualTo("My Preference");
        assertThat(preference.getDescription()).isEqualTo("Description of the preference");
        assertThat(preference.getType()).isEqualTo(Type.STRING);
        assertThat(preference.getProtectionScope()).isEqualTo(ProtectionScope.SITE);
        assertThat(preference.isMultivalue()).isTrue();
        assertThat(preference.isDisabled()).isTrue();
        assertThat(preference.isEnvironmentEnabled()).isTrue();
        assertThat(preference.getDefinitionLocation()).isEqualTo("Test case");

        assertThat(preference.getContextName()).isEqualTo("My Context");
        assertThat(preference.getValues()).isNotNull();
        assertThat(preference.getValues().size()).isEqualTo(2);
        assertThat(preference.getValues().get(0)).isEqualTo("My value #1");
        assertThat(preference.getValues().get(1)).isEqualTo("My value #2");
    }

    private PreferencePlmxml createPreferencePlmxml() {
        final PreferencePlmxml source = new PreferencePlmxml();
        source.setName("My Preference");
        source.setDescription("Description of the preference");
        source.setType("String");
        source.setProtectionScope("Site");
        source.setArray(true);
        source.setDisabled(true);
        source.setEnvironmentEnabled(true);
        source.setContext(createContextPlmxml());
        return source;
    }

    private ContextPlmxml createContextPlmxml() {
        return new ContextPlmxml().withName("My Context").withValue("My value #1").withValue("My value #2");
    }

    @Test
    public void mapCategory() {
        final CategoryPlmxml source = new CategoryPlmxml();
        source.setName("My category");
        source.setDescription("Description of the category");
        source.addPreference(createPreferencePlmxml());

        final Category category = underTest.map(source, "Test case");

        assertThat(category).isNotNull();
        assertThat(category.getName()).isEqualTo("My category");
        assertThat(category.getDescription()).isEqualTo("Description of the category");

        final List<Preference> preferences = category.getPreferences();
        assertThat(preferences).isNotNull();
        assertThat(preferences.size()).isEqualTo(1);
        assertThat(preferences.get(0).getName()).isEqualTo("My Preference");
        assertThat(preferences.get(0).getDefinitionLocation()).isEqualTo("Test case");
    }

    @Test
    public void mapCategoryWithNullDescription() {
        final CategoryPlmxml source = new CategoryPlmxml();
        source.setName("My category");
        source.setDescription(null);
        source.addPreference(createPreferencePlmxml());

        final Category category = underTest.map(source, "Test case");

        assertThat(category).isNotNull();
        assertThat(category.getName()).isEqualTo("My category");
        assertThat(category.getDescription()).isEqualTo("");

        final List<Preference> preferences = category.getPreferences();
        assertThat(preferences).isNotNull();
        assertThat(preferences.size()).isEqualTo(1);
        assertThat(preferences.get(0).getName()).isEqualTo("My Preference");
        assertThat(preferences.get(0).getDefinitionLocation()).isEqualTo("Test case");
    }

    @Test
    public void mapInvalidProtectionScope() {
        assertThat(underTest.mapProtectionScope("")).isNull();
        assertThat(underTest.mapProtectionScope(null)).isNull();
        assertThat(underTest.mapProtectionScope("BlaBlaBla")).isNull();
    }

    @Test
    public void mapPreferenceWithoutContext() {

        final PreferencePlmxml rawPreference = new PreferencePlmxml().withName("Preference name")
                .withDescription("Preference description").withType("logical").withProtectionScope("System")
                .withDisabled(false).withEnvironmentEnabled(true).withArray(true);

        final Preference preference = underTest.map(rawPreference, "Test case");

        assertThat(preference).isNotNull();
        assertThat(preference.getName()).isEqualTo("Preference name");
        assertThat(preference.getDescription()).isEqualTo("Preference description");
        assertThat(preference.getType()).isEqualTo(Type.LOGICAL);
        assertThat(preference.isMultivalue()).isTrue();
        assertThat(preference.isDisabled()).isFalse();
        assertThat(preference.isEnvironmentEnabled()).isTrue();
        assertThat(preference.getProtectionScope()).isEqualTo(ProtectionScope.SYSTEM);
        assertThat(preference.getContextName()).isNull();
        assertThat(preference.getValues()).isNull();
        assertThat(preference.getDefinitionLocation()).isEqualTo("Test case");
    }
}
