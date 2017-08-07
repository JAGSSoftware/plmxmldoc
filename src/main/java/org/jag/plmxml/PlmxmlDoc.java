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

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.jag.plmxml.domain.Arguments;
import org.jag.plmxml.domain.Categories;
import org.jag.plmxml.service.PlmxmlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;

/**
 * @author Jose A. Garcia
 */
public class PlmxmlDoc {
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmxmlDoc.class);

    /** Service. */
    private final PlmxmlService plmxmlService;

    /** Arguments object. */
    private final Arguments arguments;

    /**
     *
     * @param plmxmlService
     * @param arguments
     */
    public PlmxmlDoc(final PlmxmlService plmxmlService, final Arguments arguments) {
        this.plmxmlService = plmxmlService;
        this.arguments = arguments;
    }

    /**
     * Read the preferences from the input files specified by name in the arguments.
     *
     * @return Categories from the preferences files
     */
    public Categories readPreferences() {
        return plmxmlService.readPreferences(arguments.getInputFilenames());
    }

    /**
     * Renders the report of categories and preferences.
     * 
     * @param categories
     *            Categories to be rendered
     * @param writer
     *            report writer with categories and preferences
     */
    public void renderHtml(final Categories categories, final Writer writer) {
        final Template template = getTemplate("preference2html.ftl");
        if (template == null) {
            LOGGER.warn("No rendering has been done.");
            return;
        }

        final Map<String, Object> root = new HashMap<>();
        root.put("projectInfo", arguments.getProjectInfo());
        root.put("categories", categories);
        root.put("information", arguments.getInformation());
        try {
            template.process(root, writer);
        } catch (TemplateException e) {
            LOGGER.warn("TemplateException happened: [{}]", e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.warn("An exception happened when rendering the categories [{}]", categories, e);
        }
    }

    private Template getTemplate(final String templateName) {
        final Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(getClass(), "/templates");
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(true);

        try {
            return configuration.getTemplate(templateName);
        } catch (TemplateNotFoundException e) {
            LOGGER.warn("Template not found: [{}]", e.getMessage(), e);
        } catch (MalformedTemplateNameException e) {
            LOGGER.warn("Malformed template name: [{}]", e.getMessage(), e);
        } catch (ParseException e) {
            LOGGER.warn("Parsing exception: [{}]", e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.warn("An exception happened when returning the template with name [{}]", templateName, e);
        }
        return null;
    }
}
