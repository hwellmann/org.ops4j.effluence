/*
 * Copyright 2013 Harald Wellmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ops4j.effluence.xwiki;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.xwiki.component.embed.EmbeddableComponentManager;
import org.xwiki.component.manager.ComponentLookupException;
import org.xwiki.rendering.block.XDOM;
import org.xwiki.rendering.converter.ConversionException;
import org.xwiki.rendering.internal.parser.xhtml.XHTMLParser;
import org.xwiki.rendering.parser.ParseException;
import org.xwiki.rendering.parser.Parser;
import org.xwiki.rendering.renderer.BlockRenderer;
import org.xwiki.rendering.renderer.printer.DefaultWikiPrinter;
import org.xwiki.rendering.syntax.Syntax;

/**
 * @author Harald Wellmann
 * 
 */
public class Confluence4ParserTest {

    private EmbeddableComponentManager componentManager;
    private XHTMLParser parser;
    private BlockRenderer renderer;

    @Before
    public void setUp() throws ComponentLookupException, ConversionException {
        componentManager = new EmbeddableComponentManager();
        componentManager.initialize(this.getClass().getClassLoader());
        parser = componentManager.getInstance(Parser.class, "confluence/4.0");
        renderer = componentManager.getInstance(BlockRenderer.class, Syntax.XWIKI_2_1.toIdString());
    }

    private String convertResource(String resourcePath) {
        try {
            InputStream is = getClass().getResourceAsStream("/test-data/" + resourcePath);
            InputStreamReader reader = new InputStreamReader(is, "UTF-8");
            XDOM xdom = parser.parse(reader);
            assertThat(xdom, is(notNullValue()));
            DefaultWikiPrinter printer = new DefaultWikiPrinter();
            renderer.render(xdom, printer);
            return printer.toString();
        }
        catch (ParseException | UnsupportedEncodingException exc) {
            throw new RuntimeException(exc);
        }
    }

    private String convertMarkup(String confluenceMarkup) {
        try {
            String template = IOUtils.toString(getClass().getResource("/xml/Confluence4Template.xml"));
            String content = template.replace("@CONTENT@", confluenceMarkup);
            XDOM xdom = parser.parse(new StringReader(content));
            assertThat(xdom, is(notNullValue()));
            DefaultWikiPrinter printer = new DefaultWikiPrinter();
            renderer.render(xdom, printer);
            return printer.toString();
        }
        catch (ParseException | IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    private void checkFileConversion(String resourcePath, String result) {
        assertThat(convertResource(resourcePath), is(result));
    }

    private void checkMarkupConversion(String markup, String result) {
        assertThat(convertMarkup(markup), is(result));
    }

    @Test
    public void parseLinkFromFile() {
        checkFileConversion("link.xml",
            "=== My test works in the [[Forked Container>>url:../paxexam/Forked Container]] but not in the Native Container ===");
    }

    @Test
    public void parseLink() {
        checkMarkupConversion("<h3>My test works in the <ac:link><ri:page ri:content-title='Forked Container' ri:space-key='paxexam'/></ac:link> but not in the Native Container</h3>",
            "=== My test works in the [[Forked Container>>url:../paxexam/Forked Container]] but not in the Native Container ===");
    }

    @Test
    public void parseTt() {
        checkMarkupConversion("<h3>My test works in the <tt>Forked Container</tt> but not in the Native Container</h3>",
            "=== My test works in the ##Forked Container## but not in the Native Container ===");
    }

    @Test
    public void parseA() {
        checkMarkupConversion("<h3>My test works in the <a href='fc.html'>Forked Container</a> but not in the Native Container</h3>",
            "=== My test works in the [[Forked Container>>url:fc.html]] but not in the Native Container ===");
    }

    @Test
    public void parseLinkWithBody() {
        checkMarkupConversion("to <ac:link><ri:page ri:content-title=\"Diagnosis\" /><ac:link-body>enable an OSGI console</ac:link-body></ac:link>. The details",
            "to [[enable an OSGI console>>url:Diagnosis]]. The details");
    }
    
    @Test
    public void parseMacroWithPlainTextBody() {
        checkMarkupConversion("<ac:macro ac:name=\"code\"><ac:plain-text-body><![CDATA[\nreturn true;\n]]></ac:plain-text-body></ac:macro>",
            "{{code}}\n\nreturn true;\\\\\n{{/code}}");
    }

    @Test
    public void parseMacroWithoutBody() {
        checkMarkupConversion("<ac:macro ac:name=\"toc\" />",
            "{{toc}}{{/toc}}");
    }

    @Test
    public void parseMacroWithRichTextBody() {
        checkMarkupConversion("<ac:macro ac:name=\"info\"><ac:rich-text-body><p>Do <strong>not</strong> delete!</p></ac:rich-text-body></ac:macro>",
            "{{info}}\nDo **not** delete!\n{{/info}}");
    }

    
    
    @Test
    public void parseMacroWithDefaultParameter() {
        checkMarkupConversion("<ac:macro ac:name=\"code\"><ac:default-parameter>xml</ac:default-parameter><ac:plain-text-body><![CDATA[\n" +
            "<groupId>org.ops4j.effluence</groupId>\n" +
            "]]></ac:plain-text-body></ac:macro>",
            "{{code language=\"xml\"}}\n\n<groupId>org.ops4j.effluence</groupId>\\\\\n{{/code}}");
    }
    
    
    @Test
    public void parseMacroWithParameter() {
        checkMarkupConversion("<ac:macro ac:name='children'><ac:parameter ac:name='depth'>2</ac:parameter></ac:macro>",
            "{{children depth=\"2\"}}{{/children}}");
    }
}
