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
import java.util.List;

import org.jag.plmxml.domain.Arguments;
import org.jag.plmxml.domain.Information;
import org.jag.plmxml.domain.ProjectInfo;

/**
 * @author Jose A. Garcia
 */
class ArgumentsImpl implements Arguments {
    private static final String DEFAULT_OUTPUT_FILENAME = "output.html";
    private ProjectInfo project;
    private Information information;
    private final List<String> inputFilenames = new ArrayList<>();
    private String outputFilename = DEFAULT_OUTPUT_FILENAME;

    public ArgumentsImpl() {
        // Empty body
    }

    @Override
    public ProjectInfo getProjectInfo() {
        return project;
    }

    public void setProject(final ProjectInfo project) {
        this.project = project;
    }

    @Override
    public List<String> getInputFilenames() {
        return inputFilenames;
    }

    public void addInputFilename(final String filename) {
        this.inputFilenames.add(filename);
    }

    @Override
    public String getOutputFilename() {
        return outputFilename;
    }

    public void setOutputFilename(final String outputFilename) {
        this.outputFilename = outputFilename;
    }

    @Override
    public Information getInformation() {
        return information;
    }

    public void setInformation(final Information information) {
        this.information = information;
    }
}
