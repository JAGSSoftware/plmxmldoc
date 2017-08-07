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
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.jag.plmxml.domain.Arguments;
import org.jag.plmxml.domain.ProjectInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jose A. Garcia
 */
class ArgumentsServiceBean implements ArgumentsService {
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ArgumentsServiceBean.class);
    /** */
    private static final String ARG_INPUT_FILENAMES = "inputFilenames";
    /** */
    private static final String ARG_OUTPUT_FILENAME = "outputFilename";
    /** */
    private static final String ARG_PROJECT = "project";
    /** */
    private static final String ARG_VERSION = "version";
    /** */
    private static final List<Option> REQUIRED_OPTIONS = Arrays.asList(
            // @formatter:off
            new Option(ARG_INPUT_FILENAMES, true, "Files to be documented, separated by commas"),
            new Option(ARG_PROJECT, true, "Name of the project"),
            new Option(ARG_VERSION, true, "Version of the project")
    // formatter:on
    );

    /** */
    private static final List<Option> OPTIONAL_OPTIONS = Arrays.asList(
            // @formatter:off
            new Option(ARG_OUTPUT_FILENAME, true, "Rendered output file with the documentation")
    // formatter:on
    );

    /** */
    private final Options options = new Options();

    /**
     *
     */
    public ArgumentsServiceBean() {
        for (final Option option : REQUIRED_OPTIONS) {
            option.setRequired(true);
            options.addOption(option);
        }
        for (final Option option : OPTIONAL_OPTIONS) {
            options.addOption(option);
        }
    }

    /**
     *
     */
    @Override
    public Arguments parse(final String[] args) {
        try {
            final ArgumentsImpl arguments = new ArgumentsImpl();
            final CommandLine commandLine = new DefaultParser().parse(options, args);

            loadInputFilenames(arguments, commandLine);

            if (commandLine.hasOption(ARG_OUTPUT_FILENAME)) {
                arguments.setOutputFilename(commandLine.getOptionValue(ARG_OUTPUT_FILENAME));
            } else if (arguments.getInputFilenames().size() == 1) {
                arguments.setOutputFilename(getOutputFilename(arguments.getInputFilenames().get(0)));
            }

            final ProjectInfo project = new ProjectInfoImpl(commandLine.getOptionValue(ARG_PROJECT),
                    commandLine.getOptionValue(ARG_VERSION));
            arguments.setProject(project);
            arguments.setInformation(new InformationImpl(arguments.getInputFilenames()));

            return arguments;
        } catch (ParseException e) {
            LOGGER.warn(e.getMessage());
        }

        return null;
    }

    private void loadInputFilenames(final ArgumentsImpl arguments, final CommandLine commandLine) {
        final List<String> inputFilenamesFromArgument = getInputFilenamesFromArgument(
                commandLine.getOptionValue(ARG_INPUT_FILENAMES));
        for (final String inputFilenameFromArgument : inputFilenamesFromArgument) {
            arguments.addInputFilename(inputFilenameFromArgument);
        }
    }

    /**
     *
     * @param optionValue
     * @return
     */
    public List<String> getInputFilenamesFromArgument(final String optionValue) {
        final List<String> inputFilenamesFromArgument = new ArrayList<>();

        final StringTokenizer stringTokenizer = new StringTokenizer(optionValue, ",");
        while (stringTokenizer.hasMoreTokens()) {
            inputFilenamesFromArgument.add(stringTokenizer.nextToken());
        }

        return inputFilenamesFromArgument;
    }

    /**
     *
     * @param inputFilename
     * @return
     */
    public String getOutputFilename(final String inputFilename) {
        final Matcher matcher = Pattern.compile("(.*[\\\\|/])?([\\w \\.]+).xml").matcher(inputFilename);
        if (matcher.matches()) {
            return matcher.group(2) + ".html";
        }
        return "output.html";
    }

    /**
     *
     */
    @Override
    public void printHelp() {
        new HelpFormatter().printHelp("plmxmldoc", options);
    }
}
