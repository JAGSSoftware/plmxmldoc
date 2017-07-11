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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jag.plmxml.domain.Preference;
import org.jag.plmxml.domain.ProtectionScope;
import org.jag.plmxml.domain.Type;

/**
 * @author Jose A. Garcia
 */
class PreferenceImpl implements Preference {
    private final String name;
    private String description;
    private Type type;
    private ProtectionScope protectionScope;
    private boolean multivalue;
    private boolean disabled;
    private boolean environmentEnabled;
    private String contextName;
    private List<String> values;
    private String definitionLocation;

    public PreferenceImpl(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public Type getType() {
        return type;
    }

    public void setType(final Type type) {
        this.type = type;
    }

    @Override
    public ProtectionScope getProtectionScope() {
        return protectionScope;
    }

    public void setProtectionScope(final ProtectionScope protectionScope) {
        this.protectionScope = protectionScope;
    }

    @Override
    public boolean isMultivalue() {
        return multivalue;
    }

    public void setMultivalue(final boolean array) {
        this.multivalue = array;
    }

    @Override
    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(final boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public boolean isEnvironmentEnabled() {
        return environmentEnabled;
    }

    public void setEnvironmentEnabled(final boolean environmentEnabled) {
        this.environmentEnabled = environmentEnabled;
    }

    @Override
    public String getContextName() {
        return contextName;
    }

    public void setContextName(final String contextName) {
        this.contextName = contextName;
    }

    @Override
    public List<String> getValues() {
        if (values == null) {
            return values;
        }
        return Collections.unmodifiableList(values);
    }

    public void addValue(final String value) {
        if (values == null) {
            values = new ArrayList<>();
        }
        values.add(value);
    }

    public void addValues(final List<String> values) {
        if (this.values == null) {
            this.values = new ArrayList<>();
        }
        this.values.addAll(values);
    }

    @Override
    public String getDefinitionLocation() {
        return definitionLocation;
    }

    public void setDefinitionLocation(final String definitionLocation) {
        this.definitionLocation = definitionLocation;
    }
}
