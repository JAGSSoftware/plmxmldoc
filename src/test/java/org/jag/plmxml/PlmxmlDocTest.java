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

package org.jag.plmxml;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.jag.plmxml.domain.Arguments;
import org.jag.plmxml.domain.Categories;
import org.jag.plmxml.domain.Category;
import org.jag.plmxml.domain.Information;
import org.jag.plmxml.domain.ProjectInfo;
import org.jag.plmxml.service.PlmxmlService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Jose A. Garcia
 */
public class PlmxmlDocTest {
    @Mock
    private Arguments arguments;

    @Mock
    private PlmxmlService plmxmlService;

    private PlmxmlDoc underTest;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        underTest = new PlmxmlDoc(plmxmlService, arguments);
    }

    @Test
    public void readPreferences() {
        when(arguments.getInputFilenames()).thenReturn(Arrays.asList("inputFilename.xml"));

        underTest.readPreferences();

        verify(plmxmlService).readPreferences(Arrays.asList("inputFilename.xml"));
    }

    @Test
    public void renderHtmlToWriter() throws IOException {
        final ProjectInfo projectInfo = mock(ProjectInfo.class);
        when(projectInfo.getName()).thenReturn("My Project");
        when(projectInfo.getVersion()).thenReturn("v1.0");

        final Information information = mock(Information.class);
        when(information.getFilenames()).thenReturn(Arrays.asList("filename.xml"));
        when(information.getCurrentDate()).thenReturn(new Date());

        when(arguments.getProjectInfo()).thenReturn(projectInfo);
        when(arguments.getInformation()).thenReturn(information);
        final Categories categories = mock(Categories.class);
        when(categories.getValues()).thenReturn(new ArrayList<Category>());

        final Writer writer = mock(Writer.class);

        underTest.renderHtml(categories, writer);

        verify(writer, atLeastOnce()).write(Mockito.any(char[].class));
    }
}
