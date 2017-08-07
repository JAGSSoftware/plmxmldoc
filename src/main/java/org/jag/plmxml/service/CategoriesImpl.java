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

import org.jag.plmxml.domain.Categories;
import org.jag.plmxml.domain.Category;

/**
 * @author Jose A. Garcia
 */
class CategoriesImpl implements Categories {
    /** */
    private final List<Category> values = new ArrayList<>();
    /** */
    private int numberOfPreferences = 0;

    /**
     *
     */
    @Override
    public List<Category> getValues() {
        return Collections.unmodifiableList(values);
    }

    /**
     *
     * @param category
     */
    public void add(final Category category) {
        values.add(category);
        incrementNumberOfPreferences(category.getPreferences().size());
    }

    /**
     *
     */
    @Override
    public int getNumberOfCategories() {
        return values.size();
    }

    /**
     *
     */
    @Override
    public int getNumberOfPreferences() {
        return numberOfPreferences;
    }

    /**
     *
     * @param numberOfPreferences
     */
    public void incrementNumberOfPreferences(final int numberOfPreferences) {
        this.numberOfPreferences += numberOfPreferences;
    }

    /**
     *
     * @param category
     * @return
     */
    public Category findCategory(final Category category) {
        final int index = values.indexOf(category);
        if (index >= 0) {
            return values.get(index);
        }
        return null;
    }
}
