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
package test;

import static com.google.common.truth.Truth.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.jag.plmxml.xml.CategoryPlmxml;
import org.jag.plmxml.xml.PreferencePlmxml;
import org.jag.plmxml.xml.PreferencesPlmxml;
import org.junit.After;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * @author Jose A. Garcia
 */
public class TestSchemaValidation {
    private static final String FILENAME = "output.xml";

    @After
    public void tearDown() {

        final File file = new File(FILENAME);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void readXml() throws SAXException, JAXBException, FileNotFoundException {
        final JAXBContext jaxbContext = JAXBContext.newInstance(PreferencesPlmxml.class);

        final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        final PreferencesPlmxml preferences = (PreferencesPlmxml) unmarshaller
                .unmarshal(new FileReader("src/test/resources/preferences.xml"));

        assertThat(preferences).isNotNull();
        assertThat(preferences.getVersion()).isEqualTo("10.0");
        assertThat(preferences.getCategories()).isNotNull();
        assertThat(preferences.getCategories()).isNotEmpty();
        assertThat(preferences.getCategories().size()).isEqualTo(11);
        final CategoryPlmxml category = preferences.getCategories().get(0);
        assertThat(category.getName()).isEqualTo("Category #1");
        assertThat(category.getDescription()).isEqualTo("Description for Category #1");
        assertThat(category.getPreferences()).isNotNull();
        assertThat(category.getPreferences()).isNotEmpty();
        assertThat(category.getPreferences().size()).isEqualTo(12);

        final PreferencePlmxml preference = category.getPreferences().get(0);
        assertThat(preference.getName()).isEqualTo("J4G_NOTREALPREF");
        assertThat(preference.getType()).isEqualTo("String");
        assertThat(preference.isArray()).isFalse();
        assertThat(preference.isDisabled()).isTrue();
        assertThat(preference.getProtectionScope()).isEqualTo("Site");
        assertThat(preference.isEnvironmentEnabled()).isFalse();
        assertThat(preference.getDescription()).isEqualTo("Not a real preference");
        assertThat(preference.getContext()).isNotNull();
        assertThat(preference.getContext().getName()).isEqualTo("Teamcenter");

        assertThat(category.getPreferences().get(11)).isNotNull();
        assertThat(category.getPreferences().get(11).getContext()).isNotNull();
        assertThat(category.getPreferences().get(11).getContext().getName()).isEqualTo("Teamcenter");
        assertThat(category.getPreferences().get(11).getContext().getValues()).isNotNull();
        assertThat(category.getPreferences().get(11).getContext().getValues()).isNotEmpty();
        assertThat(category.getPreferences().get(11).getContext().getValues().size()).isEqualTo(1);
    }

    @Test
    public void writeXml() throws JAXBException {
        final PreferencePlmxml preference = new PreferencePlmxml();
        preference.setName("NOTREALPREF");
        preference.setType("String");
        preference.setArray(false);
        preference.setDisabled(true);
        preference.setProtectionScope("Site");
        preference.setEnvironmentEnabled(true);

        final CategoryPlmxml category = new CategoryPlmxml();
        category.setDescription("Description for the category");
        category.setName("One first category");
        category.addPreference(preference);
        final PreferencesPlmxml preferences = new PreferencesPlmxml();
        preferences.setVersion("12.3");
        preferences.addCategory(category);

        final JAXBContext jaxbContext = JAXBContext.newInstance(PreferencesPlmxml.class);
        final Marshaller marshaller = jaxbContext.createMarshaller();
        try (final FileWriter fileWriter = new FileWriter(FILENAME)) {
            marshaller.marshal(preferences, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
