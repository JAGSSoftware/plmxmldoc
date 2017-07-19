/*
 * Copyright (C) 2017 Jose A. Garcia Sanchez
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package org.jag.plmxml.domain;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Jose A. Garcia
 */
public class CategoryTest {

    private Category underTest;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        underTest = new Category("category name");
    }

    @Test
    public void isEqualToItself() {
        assertThat(underTest.equals(underTest)).isTrue();
    }

    @Test
    public void isEqualToAnyOtherCategoryWithSameName() {
        final Category otherCategory = new Category("category name");

        assertThat(underTest).isNotSameAs(otherCategory);
        assertThat(underTest).isEqualTo(otherCategory);
        assertThat(otherCategory).isEqualTo(underTest);

        assertThat(underTest.hashCode()).isEqualTo(otherCategory.hashCode());
    }

    @Test
    public void isNotEqualToAnyOtherCategoryWithDifferentName() {
        final Category otherCategory = new Category("any category name");

        assertThat(underTest).isNotSameAs(otherCategory);
        assertThat(underTest).isNotEqualTo(otherCategory);
        assertThat(otherCategory).isNotEqualTo(underTest);

        assertThat(underTest.hashCode()).isNotEqualTo(otherCategory.hashCode());
    }

    @Test
    public void isNotEqualToAnyOtherObjectType() {
        assertThat(underTest).isNotEqualTo("bla, bla");
    }

    @Test
    public void isNotEqualToNullObject() {
        assertThat(underTest).isNotEqualTo(null);
    }
}
