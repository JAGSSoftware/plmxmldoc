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

package org.jag.plmxml.service;

import static com.google.common.truth.Truth.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Jose A. Garcia Sanchez
 */
public class PreferenceImplTest {

    private PreferenceImpl underTest;

    @Before
    public void setUp() throws Exception {
        underTest = new PreferenceImpl("preference");
    }

    @Test
    public void getValuesWithoutAdding() {
        assertThat(underTest.getValues()).isNull();
    }

    @Test
    public void addValue() {
        underTest.addValue("value #1");

        final List<String> values = underTest.getValues();
        assertThat(values).isNotNull();
        assertThat(values.size()).isEqualTo(1);
        assertThat(values.get(0)).isEqualTo("value #1");
    }

    @Test
    public void addValueTwice() {
        underTest.addValue("value #1");
        underTest.addValue("value #2");

        final List<String> values = underTest.getValues();
        assertThat(values).isNotNull();
        assertThat(values.size()).isEqualTo(2);
        assertThat(values.get(0)).isEqualTo("value #1");
        assertThat(values.get(1)).isEqualTo("value #2");
    }

    @Test
    public void addValues() {
        underTest.addValues(Arrays.asList("value #1", "value #2"));

        final List<String> values = underTest.getValues();
        assertThat(values).isNotNull();
        assertThat(values.size()).isEqualTo(2);
        assertThat(values.get(0)).isEqualTo("value #1");
        assertThat(values.get(1)).isEqualTo("value #2");
    }

    @Test
    public void addValuesTwice() {
        underTest.addValues(Arrays.asList("value #1", "value #2"));
        underTest.addValues(Arrays.asList("value #3", "value #4"));

        final List<String> values = underTest.getValues();
        assertThat(values).isNotNull();
        assertThat(values.size()).isEqualTo(4);
        assertThat(values.get(0)).isEqualTo("value #1");
        assertThat(values.get(1)).isEqualTo("value #2");
        assertThat(values.get(2)).isEqualTo("value #3");
        assertThat(values.get(3)).isEqualTo("value #4");
    }
}
