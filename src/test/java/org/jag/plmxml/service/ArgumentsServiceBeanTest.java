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

import static com.google.common.truth.Truth.assertThat;

import java.util.List;

import org.jag.plmxml.domain.Arguments;
import org.jag.plmxml.domain.Information;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;

/**
 * @author Jose A. Garcia
 */
@RunWith(JUnitParamsRunner.class)
public class ArgumentsServiceBeanTest {
    private ArgumentsServiceBean underTest;

    @Before
    public void setUp() throws Exception {
        underTest = new ArgumentsServiceBean();
    }

    @Test
    public void mandatoryArguments() {
        final String[] args = new String[] { "-inputFilenames", "sample.xml", "-project", "JAG Proj", "-version",
                "1.2", };

        final Arguments arguments = underTest.parse(args);

        assertThat(arguments).isNotNull();
        assertThat(arguments.getInputFilenames()).isNotNull();
        assertThat(arguments.getInputFilenames()).hasSize(1);
        assertThat(arguments.getInputFilenames().get(0)).isEqualTo("sample.xml");
        assertThat(arguments.getOutputFilename()).isEqualTo("sample.html");
        assertThat(arguments.getProjectInfo()).isNotNull();
        assertThat(arguments.getProjectInfo().getName()).isEqualTo("JAG Proj");
        assertThat(arguments.getProjectInfo().getVersion()).isEqualTo("1.2");
    }

    @Test
    public void allArguments() {
        final String[] args = new String[] { "-inputFilenames", "sample.xml", "-outputFilename", "preferences.html",
                "-project", "JAG Proj", "-version", "1.2", };

        final Arguments arguments = underTest.parse(args);

        assertThat(arguments).isNotNull();
        assertThat(arguments.getInputFilenames()).hasSize(1);
        assertThat(arguments.getInputFilenames().get(0)).isEqualTo("sample.xml");
        assertThat(arguments.getOutputFilename()).isEqualTo("preferences.html");
        assertThat(arguments.getProjectInfo()).isNotNull();
        assertThat(arguments.getProjectInfo().getName()).isEqualTo("JAG Proj");
        assertThat(arguments.getProjectInfo().getVersion()).isEqualTo("1.2");
    }

    public static Object[] requiredArgumentsTestData() {
        return new Object[][] { new String[] { "-project", "JAG Proj", "-version", "1.2" },
                new String[] { "-filename", "sample.xml", "-version", "1.2" },
                new String[] { "-filename", "sample.xml", "-project", "JAG Proj" }, };
    }

    @Test
    @Parameters(method = "requiredArgumentsTestData")
    public void requiredArguments(final String[] args) {
        final Arguments arguments = underTest.parse(args);

        assertThat(arguments).isNull();
    }

    @Test
    @Parameters(value = { "preferences.xml,preferences.html", "org.jag.preferences.xml,org.jag.preferences.html",
            "site/preferences.xml,preferences.html", "site\\preferences.xml,preferences.html", })
    public void calculateOutputFilename(final String inputFilename, final String outputFilename) {
        final String[] args = new String[] { "-inputFilenames", inputFilename, "-project", "JAG Proj", "-version",
                "1.2", };

        final Arguments arguments = underTest.parse(args);

        assertThat(arguments).isNotNull();
        assertThat(arguments.getInputFilenames()).hasSize(1);
        assertThat(arguments.getInputFilenames().get(0)).isEqualTo(inputFilename);
        assertThat(arguments.getOutputFilename()).isEqualTo(outputFilename);
    }

    @Test
    @Parameters(value = { "file.txt", ".xml" })
    @TestCaseName("inputFilename: [{0}]")
    public void getOutputFilenameForInvalidInputFilenames(final String inputFilename) {
        assertThat(underTest.getOutputFilename(inputFilename)).isEqualTo("output.html");
    }

    @Test
    @Parameters(value = { "file.xml,file.html", "path/file.xml,file.html", "path\\file.xml,file.html",
            "path/path.to.file.xml,path.to.file.html", "file1.2.3.xml,file1.2.3.html" })
    @TestCaseName("inputFilename: [{0}], expected outputFilename: [{1}]")
    public void getOutputFilename(final String inputFilename, final String outputFilename) {
        assertThat(underTest.getOutputFilename(inputFilename)).isEqualTo(outputFilename);
    }

    @Test
    public void getInputFilenamesFromArgument() {
        final List<String> inputFilenamesFromArgument = underTest
                .getInputFilenamesFromArgument("sample1.xml,sample2.xml,sample3.xml");

        assertThat(inputFilenamesFromArgument).isNotNull();
        assertThat(inputFilenamesFromArgument).hasSize(3);
        assertThat(inputFilenamesFromArgument).containsAllOf("sample1.xml", "sample2.xml", "sample3.xml");
    }

    @Test
    public void getInputFilenamesFromEmptyArgument() {

        final List<String> inputFilenamesFromArgument = underTest.getInputFilenamesFromArgument("");

        assertThat(inputFilenamesFromArgument).isNotNull();
        assertThat(inputFilenamesFromArgument).isEmpty();
    }

    @Test
    public void listOfInputFilenamesWithOutputFilename() {

        final String[] args = new String[] { "-inputFilenames", "sample1.xml,sample2.xml,sample3.xml",
                "-outputFilename", "preferences.html", "-project", "JAG Proj", "-version", "1.2", };

        final Arguments arguments = underTest.parse(args);

        assertThat(arguments).isNotNull();
        assertThat(arguments.getInputFilenames()).hasSize(3);
        assertThat(arguments.getInputFilenames().get(0)).isEqualTo("sample1.xml");
        assertThat(arguments.getInputFilenames().get(1)).isEqualTo("sample2.xml");
        assertThat(arguments.getInputFilenames().get(2)).isEqualTo("sample3.xml");
        assertThat(arguments.getOutputFilename()).isEqualTo("preferences.html");
        assertThat(arguments.getProjectInfo()).isNotNull();
        assertThat(arguments.getProjectInfo().getName()).isEqualTo("JAG Proj");
        assertThat(arguments.getProjectInfo().getVersion()).isEqualTo("1.2");
    }

    @Test
    public void listOfInputFilenamesWithoutOutputFilename() {

        final String[] args = new String[] { "-inputFilenames", "sample1.xml,sample2.xml,sample3.xml", "-project",
                "JAG Proj", "-version", "1.2", };

        final Arguments arguments = underTest.parse(args);

        assertThat(arguments).isNotNull();
        assertThat(arguments.getInputFilenames()).hasSize(3);
        assertThat(arguments.getInputFilenames().get(0)).isEqualTo("sample1.xml");
        assertThat(arguments.getInputFilenames().get(1)).isEqualTo("sample2.xml");
        assertThat(arguments.getInputFilenames().get(2)).isEqualTo("sample3.xml");
        assertThat(arguments.getOutputFilename()).isEqualTo("output.html");
        assertThat(arguments.getProjectInfo()).isNotNull();
        assertThat(arguments.getProjectInfo().getName()).isEqualTo("JAG Proj");
        assertThat(arguments.getProjectInfo().getVersion()).isEqualTo("1.2");
    }

    @Test
    public void informationWithListOfInputFilenames() {
        final String[] args = new String[] { "-inputFilenames", "sample1.xml,sample2.xml,sample3.xml",
                "-outputFilename", "preferences.html", "-project", "JAG Proj", "-version", "1.2", };

        final Information information = underTest.parse(args).getInformation();

        assertThat(information).isNotNull();
        assertThat(information.getFilenames()).isNotNull();
        assertThat(information.getFilenames()).isNotEmpty();
        assertThat(information.getFilenames()).hasSize(3);
        assertThat(information.getFilenames()).containsAllOf("sample1.xml", "sample2.xml", "sample3.xml");

    }
}
