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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.jag.plmxml.domain.Categories;
import org.jag.plmxml.domain.Category;
import org.jag.plmxml.domain.Preference;
import org.jag.plmxml.xml.CategoryPlmxml;
import org.jag.plmxml.xml.PreferencesPlmxml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

/**
 * @author Jose A. Garcia
 */
class PlmxmlServiceBean implements PlmxmlService {
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmxmlServiceBean.class);

    /** Mapper between domain objects. */
    private final PlmxmlMapper mapper;

    /**
     * Constructor.
     *
     * @param mapper
     *            Domain objects mapper
     */
    @Inject
    public PlmxmlServiceBean(final PlmxmlMapper mapper) {
        this.mapper = mapper;
    }

    /**
     *
     */
    @Override
    public Categories readPreferences(final String inputFilename) {
        try (final Reader reader = new InputStreamReader(new FileInputStream(inputFilename),
                Charset.forName("UTF-8"))) {
            return readPreferences(reader, inputFilename);
        } catch (FileNotFoundException e) {
            LOGGER.warn("File not found: [{}]", e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.warn("IOException happened: [{}]", e.getMessage(), e);
        }
        return new CategoriesImpl();
    }

    /**
     *
     * @param reader
     * @param definedIn
     * @return
     */
    public Categories readPreferences(final Reader reader, final String definedIn) {
        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance(PreferencesPlmxml.class);
            final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return merge(map((PreferencesPlmxml) unmarshaller.unmarshal(reader), definedIn));
        } catch (JAXBException e) {
            LOGGER.info("Error when unmarshalling the preference defined in [{}]", definedIn, e);
        }

        return new CategoriesImpl();
    }

    /**
     *
     * @param preferences
     * @param definedIn
     * @return
     */
    public List<Category> map(final PreferencesPlmxml preferences, final String definedIn) {
        if (preferences == null) {
            return new ArrayList<>();
        }

        final List<Category> categories = new ArrayList<>();
        for (final CategoryPlmxml rawCategory : preferences.getCategories()) {
            categories.add(mapper.map(rawCategory, definedIn));
        }
        return categories;
    }

    /**
     *
     * @param categories
     * @return
     */
    public Categories merge(final List<Category> categories) {
        final CategoriesImpl mergedCategories = new CategoriesImpl();
        merge(mergedCategories, categories);
        return mergedCategories;
    }

    /**
     *
     * @param mergedCategories
     * @param categories
     */
    public void merge(final CategoriesImpl mergedCategories, final List<Category> categories) {
        for (final Category category : categories) {
            final Category existingCategory = mergedCategories.findCategory(category);
            if (existingCategory == null) {
                mergedCategories.add(category);
            } else {
                for (final Preference preference : category.getPreferences()) {
                    existingCategory.add(preference);
                }
                mergedCategories.incrementNumberOfPreferences(category.getPreferences().size());
            }
        }
    }

    /**
     *
     */
    @Override
    public Categories readPreferences(final List<String> inputFilenames) {
        final CategoriesImpl mergedCategories = new CategoriesImpl();

        for (final String inputFilename : inputFilenames) {
            merge(mergedCategories, readPreferences(inputFilename).getValues());
        }

        return mergedCategories;
    }
}
