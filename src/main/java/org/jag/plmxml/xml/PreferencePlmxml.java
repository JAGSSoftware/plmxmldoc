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
package org.jag.plmxml.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Jose A. Garcia
 */
@XmlRootElement(name = "preference")
public class PreferencePlmxml {
    private String name;
    private String type;
    private boolean array;
    private boolean disabled;
    private String protectionScope;
    private String description;
    private ContextPlmxml context;
    private boolean environmentEnabled;

    public PreferencePlmxml() {
        // Empty body
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlAttribute
    public boolean isArray() {
        return array;
    }

    public void setArray(boolean array) {
        this.array = array;
    }

    @XmlAttribute
    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @XmlAttribute
    public String getProtectionScope() {
        return protectionScope;
    }

    public void setProtectionScope(String protectionScope) {
        this.protectionScope = protectionScope;
    }

    @XmlAttribute(name = "envEnabled")
    public boolean isEnvironmentEnabled() {
        return environmentEnabled;
    }

    public void setEnvironmentEnabled(boolean enabled) {
        this.environmentEnabled = enabled;
    }

    @XmlElement(name = "preference_description")
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public ContextPlmxml getContext() {
        return context;
    }

    public void setContext(final ContextPlmxml context) {
        this.context = context;
    }

    public PreferencePlmxml withName(final String name) {
        setName(name);
        return this;
    }

    public PreferencePlmxml withType(final String type) {
        setType(type);
        return this;
    }

    public PreferencePlmxml withArray(final boolean array) {
        setArray(array);
        return this;
    }

    public PreferencePlmxml withDisabled(final boolean disabled) {
        setDisabled(disabled);
        return this;
    }

    public PreferencePlmxml withProtectionScope(final String protectionScope) {
        setProtectionScope(protectionScope);
        return this;
    }

    public PreferencePlmxml withDescription(final String description) {
        setDescription(description);
        return this;
    }

    public PreferencePlmxml withContext(final ContextPlmxml context) {
        setContext(context);
        return this;
    }

    public PreferencePlmxml withEnvironmentEnabled(final boolean environmentEnabled) {
        setEnvironmentEnabled(environmentEnabled);
        return this;
    }
}
