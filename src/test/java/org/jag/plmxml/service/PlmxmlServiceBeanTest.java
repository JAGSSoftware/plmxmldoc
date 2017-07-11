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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import org.jag.plmxml.domain.Categories;
import org.jag.plmxml.domain.Category;
import org.jag.plmxml.xml.CategoryPlmxml;
import org.jag.plmxml.xml.PreferencePlmxml;
import org.jag.plmxml.xml.PreferencesPlmxml;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author Jose A. Garcia
 *
 */
public class PlmxmlServiceBeanTest {
    private PlmxmlServiceBean underTest;

    @Mock
    private PlmxmlMapper plmxmlMapperMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        underTest = new PlmxmlServiceBean(plmxmlMapperMock);

        when(plmxmlMapperMock.map(any(CategoryPlmxml.class), anyString())).then(new Answer<Category>() {

            @Override
            public Category answer(final InvocationOnMock invocation) throws Throwable {
                return new PlmxmlMapperBean().map(invocation.getArgumentAt(0, CategoryPlmxml.class),
                        invocation.getArgumentAt(1, String.class));
            }
        });
    }

    @Test
    public void readPreferencesFromFile() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        stringBuilder.append("<preferences version=\"10.0\">");
        stringBuilder.append("<category name=\"Category #1\">");
        stringBuilder.append("</category>");
        stringBuilder.append("<category name=\"Category #2\">");
        stringBuilder.append("</category>");
        stringBuilder.append("</preferences>");

        final Reader reader = new StringReader(stringBuilder.toString());
        final Categories categories = underTest.readPreferences(reader, "Test case");

        assertThat(categories).isNotNull();
        assertThat(categories.getValues()).isNotNull();
        assertThat(categories.getValues()).hasSize(2);
        assertThat(categories.getNumberOfCategories()).isEqualTo(2);
        assertThat(categories.getNumberOfPreferences()).isEqualTo(0);
    }

    @Test
    public void mapNullPreferences() {
        assertThat(underTest.map(null, "Test case")).isNotNull();
    }

    @Test
    public void mapNoCategories() {
        final PreferencesPlmxml preferencesXml = new PreferencesPlmxml();

        final List<Category> categories = underTest.map(preferencesXml, "Test case");
        assertThat(categories).isNotNull();
        assertThat(categories).isEmpty();
    }

    @Test
    public void mapCategories() {
        final PreferencePlmxml preference11 = new PreferencePlmxml().withName("preference11").withType("Double");
        final PreferencePlmxml preference21 = new PreferencePlmxml().withName("preference21").withType("String");
        final PreferencePlmxml preference22 = new PreferencePlmxml().withName("preference22").withType("Integer");
        final PreferencePlmxml preference31 = new PreferencePlmxml().withName("preference31").withType("Logical");
        final PreferencePlmxml preference32 = new PreferencePlmxml().withName("preference32").withType("Double");
        final CategoryPlmxml firstCategory = new CategoryPlmxml().withName("First category")
                .withDescription("Description for first category").withPreference(preference11);
        final CategoryPlmxml secondCategory = new CategoryPlmxml().withName("Second category")
                .withDescription("Description for second category").withPreference(preference21)
                .withPreference(preference22);
        final CategoryPlmxml thirdCategory = new CategoryPlmxml().withName("First category")
                .withPreference(preference31).withPreference(preference32);
        final PreferencesPlmxml preferencesXml = new PreferencesPlmxml().withCategory(firstCategory)
                .withCategory(secondCategory).withCategory(thirdCategory);

        final List<Category> categories = underTest.map(preferencesXml, "Test case");

        assertThat(categories).isNotNull();
        assertThat(categories).hasSize(3);
        assertThat(categories.get(0)).isNotNull();
        assertThat(categories.get(0).getName()).isEqualTo("First category");
        assertThat(categories.get(0).getPreferences()).hasSize(1);

        assertThat(categories.get(1)).isNotNull();
        assertThat(categories.get(1).getName()).isEqualTo("Second category");
        assertThat(categories.get(1).getPreferences()).hasSize(2);

        assertThat(categories.get(2)).isNotNull();
        assertThat(categories.get(2).getName()).isEqualTo("First category");
        assertThat(categories.get(2).getPreferences()).hasSize(2);
    }

    @Test
    public void mergeCategories() {
        final PreferencePlmxml preference11 = new PreferencePlmxml().withName("preference11").withType("Double");
        final PreferencePlmxml preference21 = new PreferencePlmxml().withName("preference21").withType("String");
        final PreferencePlmxml preference22 = new PreferencePlmxml().withName("preference22").withType("Integer");
        final PreferencePlmxml preference31 = new PreferencePlmxml().withName("preference31").withType("Logical");
        final PreferencePlmxml preference32 = new PreferencePlmxml().withName("preference32").withType("Double");
        final CategoryPlmxml firstCategory = new CategoryPlmxml().withName("First category")
                .withDescription("Description for first category").withPreference(preference11);
        final CategoryPlmxml secondCategory = new CategoryPlmxml().withName("Second category")
                .withDescription("Description for second category").withPreference(preference21)
                .withPreference(preference22);
        final CategoryPlmxml thirdCategory = new CategoryPlmxml().withName("First category")
                .withPreference(preference31).withPreference(preference32);
        final PreferencesPlmxml preferencesXml = new PreferencesPlmxml().withCategory(firstCategory)
                .withCategory(secondCategory).withCategory(thirdCategory);

        final List<Category> categories = underTest.map(preferencesXml, "Test case");
        assertThat(categories).isNotNull();
        assertThat(categories).hasSize(3);

        final Categories mergedCategories = underTest.merge(categories);

        assertThat(mergedCategories).isNotNull();
        assertThat(mergedCategories.getNumberOfCategories()).isEqualTo(2);
        assertThat(mergedCategories.getNumberOfPreferences()).isEqualTo(5);
        assertThat(mergedCategories.getValues()).hasSize(2);
        assertThat(mergedCategories.getValues().get(0)).isNotNull();
        assertThat(mergedCategories.getValues().get(0).getName()).isEqualTo("First category");
        assertThat(mergedCategories.getValues().get(0).getPreferences()).hasSize(3);

        assertThat(mergedCategories.getValues().get(1)).isNotNull();
        assertThat(mergedCategories.getValues().get(1).getName()).isEqualTo("Second category");
        assertThat(mergedCategories.getValues().get(1).getPreferences()).hasSize(2);
    }

    @Test
    public void readPreferencesFileNotFound() {
        final Categories categories = underTest.readPreferences("fileNotFound.xml");

        assertThat(categories).isNotNull();
        assertThat(categories.getValues()).isEmpty();
    }

    @Test
    public void readPreferences() {
        final Categories categories = underTest.readPreferences("src/test/resources/preferences.xml");

        assertThat(categories).isNotNull();
        assertThat(categories.getValues()).isNotEmpty();
        assertThat(categories.getValues()).hasSize(6);
        assertThat(categories.getValues().get(0).getPreferences()).isNotEmpty();
        assertThat(categories.getValues().get(0).getPreferences()).hasSize(12);
        assertThat(categories.getValues().get(0).getPreferences().get(0).getDefinitionLocation())
                .isEqualTo("src/test/resources/preferences.xml");
    }

    @Test
    public void readPreferencesFromSeveralFiles() {
        final Categories categories = underTest.readPreferences(Arrays.asList("src/test/resources/preferences.xml"));

        assertThat(categories).isNotNull();
        assertThat(categories.getValues()).isNotEmpty();
        assertThat(categories.getValues()).hasSize(6);
        assertThat(categories.getValues().get(0).getPreferences()).isNotEmpty();
        assertThat(categories.getValues().get(0).getPreferences()).hasSize(12);
        assertThat(categories.getValues().get(0).getPreferences().get(0).getDefinitionLocation())
                .isEqualTo("src/test/resources/preferences.xml");
    }
}
