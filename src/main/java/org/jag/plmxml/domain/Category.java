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
package org.jag.plmxml.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.common.base.MoreObjects;

/**
 * @author Jose A. Garcia
 */
public class Category {
    /** */
    private final String name;
    /** */
    private String description;
    /** */
    private final List<Preference> preferences = new ArrayList<>();

    /**
     *
     * @param name
     */
    public Category(final String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
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
    public List<Preference> getPreferences() {
        return preferences;
    }

    /**
     *
     * @param preference
     */
    public void add(final Preference preference) {
        preferences.add(preference);
    }

    /**
     *
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("super.hashCode", super.hashCode()).add("hashCode", hashCode())
                .add("name", name).add("description", description).add("[preferences]", preferences.size()).toString();
    }

    /**
     *
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    /**
     *
     */
    @Override
    public boolean equals(final Object other) {
        if (other instanceof Category) {
            return name.equals(((Category) other).getName());
        }
        return false;
    }
}
