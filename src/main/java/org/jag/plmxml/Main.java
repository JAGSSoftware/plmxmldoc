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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import org.jag.plmxml.domain.Arguments;
import org.jag.plmxml.domain.Categories;
import org.jag.plmxml.service.ArgumentsService;
import org.jag.plmxml.service.PlmxmlService;
import org.jag.plmxml.service.ServiceModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author Jose A. Garcia
 */
public final class Main {
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private Main() {
        // Empty body
    }

    /**
     * Main function
     *
     * @param args
     *            program arguments as listed in the command line
     */
    public static void main(String[] args) {
        final Injector injector = Guice.createInjector(new ServiceModule());
        final ArgumentsService argumentsService = injector.getInstance(ArgumentsService.class);

        final Arguments arguments = argumentsService.parse(args);
        if (arguments == null) {
            argumentsService.printHelp();
            return;
        }

        final PlmxmlDoc plmxmlDoc = new PlmxmlDoc(injector.getInstance(PlmxmlService.class), arguments);
        final Categories categories = plmxmlDoc.readPreferences();
        try (final Writer writer = new OutputStreamWriter(new FileOutputStream(arguments.getOutputFilename()),
                Charset.forName("UTF-8"))) {
            plmxmlDoc.renderHtml(categories, writer);
        } catch (IOException e) {
            LOGGER.error("Error when rendering the categories [{}]", categories, e);
        }
    }
}
