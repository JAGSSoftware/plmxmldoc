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

import org.jag.plmxml.domain.Category;
import org.jag.plmxml.domain.Preference;
import org.jag.plmxml.domain.ProtectionScope;
import org.jag.plmxml.domain.Type;
import org.jag.plmxml.xml.CategoryPlmxml;
import org.jag.plmxml.xml.PreferencePlmxml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

/**
 * @author Jose A. Garcia
 */
class PlmxmlMapperBean implements PlmxmlMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmxmlMapperBean.class);

    public PlmxmlMapperBean() {
        // Empty body
    }

    @Override
    public Category map(final CategoryPlmxml source, final String definitionLocation) {
        final Category category = new Category(source.getName());
        category.setDescription(Strings.nullToEmpty(source.getDescription()));
        for (final PreferencePlmxml preference : source.getPreferences()) {
            category.add(map(preference, definitionLocation));
        }

        return category;
    }

    public Preference map(final PreferencePlmxml source, final String definitionLocation) {
        final PreferenceImpl preference = new PreferenceImpl(source.getName());
        preference.setDescription(source.getDescription());
        preference.setType(mapType(source.getType()));
        preference.setProtectionScope(mapProtectionScope(source.getProtectionScope()));
        preference.setMultivalue(source.isArray());
        preference.setDisabled(source.isDisabled());
        preference.setEnvironmentEnabled(source.isEnvironmentEnabled());
        if (source.getContext() != null) {
            preference.setContextName(source.getContext().getName());
            preference.addValues(source.getContext().getValues());
        }
        preference.setDefinitionLocation(definitionLocation);

        return preference;
    }

    public Type mapType(final String type) {
        return Type.valueOf(type.toUpperCase());
    }

    public ProtectionScope mapProtectionScope(final String protectionScope) {
        if (Strings.isNullOrEmpty(protectionScope)
                || !ProtectionScope.getNames().contains(protectionScope.toUpperCase())) {
            LOGGER.warn("ProtectionScope [{}] not identified.", protectionScope);
            return null;
        }

        return ProtectionScope.valueOf(protectionScope.toUpperCase());
    }
}
