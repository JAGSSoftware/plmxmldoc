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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Jose A. Garcia
 */
@XmlRootElement(name = "category")
public class CategoryPlmxml {
    /** */
    private String name;
    /** */
    private String description;
    /** */
    private final List<PreferencePlmxml> preferences;

    /**
     *
     */
    public CategoryPlmxml() {
        this.preferences = new ArrayList<>();
    }

    /**
     *
     * @return
     */
    @XmlAttribute
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    @XmlElement(name = "category_description")
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    @XmlElement(name = "preference")
    public List<PreferencePlmxml> getPreferences() {
        return preferences;
    }

    /**
     *
     * @param preference
     */
    public void addPreference(final PreferencePlmxml preference) {
        preferences.add(preference);
    }

    /**
     *
     * @param name
     * @return
     */
    public CategoryPlmxml withName(final String name) {
        setName(name);
        return this;
    }

    /**
     *
     * @param categoryDescription
     * @return
     */
    public CategoryPlmxml withDescription(final String categoryDescription) {
        setDescription(categoryDescription);
        return this;
    }

    /**
     *
     * @param preference
     * @return
     */
    public CategoryPlmxml withPreference(final PreferencePlmxml preference) {
        addPreference(preference);
        return this;
    }
}
