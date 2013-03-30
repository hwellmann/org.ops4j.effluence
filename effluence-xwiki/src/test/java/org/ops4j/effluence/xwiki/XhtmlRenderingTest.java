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

import java.io.InputStreamReader;
import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;
import org.xwiki.component.embed.EmbeddableComponentManager;
import org.xwiki.component.manager.ComponentLookupException;
import org.xwiki.rendering.converter.ConversionException;
import org.xwiki.rendering.converter.Converter;
import org.xwiki.rendering.renderer.printer.DefaultWikiPrinter;
import org.xwiki.rendering.renderer.printer.WikiPrinter;
import org.xwiki.rendering.syntax.Syntax;

/**
 * @author Harald Wellmann
 * 
 */
public class XhtmlRenderingTest {

    private Converter converter;
    private WikiPrinter printer;

    @Before
    public void setUp() throws ComponentLookupException, ConversionException {
        EmbeddableComponentManager componentManager = new EmbeddableComponentManager();
        componentManager.initialize(this.getClass().getClassLoader());

        converter = componentManager.getInstance(Converter.class);
        printer = new DefaultWikiPrinter();
    }
    
    @Test
    public void renderXhtml() throws ComponentLookupException, ConversionException {
        converter.convert(new StringReader("This is **bold**"), Syntax.XWIKI_2_1, Syntax.XHTML_1_0,
            printer);
        
        System.out.println(printer.toString());
    }
    
    @Test
    public void renderXhtmlToXwiki() throws ComponentLookupException, ConversionException {
        converter.convert(new StringReader("<macro>This is <strong>bold</strong>.</macro>"), Syntax.XHTML_1_0, Syntax.XWIKI_2_1, 
            printer);
        
        System.out.println(printer.toString());
    }
    
    @Test
    public void renderToc() throws ComponentLookupException, ConversionException {
        converter.convert(new StringReader("One. two.\n{{toc}}{{/toc}}three four."), Syntax.XWIKI_2_1, Syntax.XDOMXML_CURRENT,
            printer);
        
        System.out.println(printer.toString());
    }
    
    @Test
    public void renderTocXhtml() throws ComponentLookupException, ConversionException {
        converter.convert(new StringReader("Prefix\n{{toc}}{{/toc}}Text"), Syntax.XWIKI_2_1, Syntax.ANNOTATED_XHTML_1_0,
            printer);
        
        System.out.println(printer.toString());
    }
    
    @Test
    public void renderConfluence3() throws ComponentLookupException, ConversionException {
        InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream("/test-data/confluence3.xml"));
        converter.convert(reader, Syntax.CONFLUENCE_1_0, Syntax.XWIKI_2_1,
            printer);
        
        System.out.println(printer.toString());
    }
    
    @Test
    public void renderInfoXhtml() throws ComponentLookupException, ConversionException {
        converter.convert(new StringReader("Prefix\n{{info}}\nTake **good**\ncare!\n{{/info}}\nSuffix"), Syntax.XWIKI_2_1, Syntax.ANNOTATED_XHTML_1_0,
            printer);
        
        System.out.println(printer.toString());
    }
    
    @Test
    public void renderInfoXdom() throws ComponentLookupException, ConversionException {
        converter.convert(new StringReader("Prefix\n{{info}}\nTake **good**\ncare!\n{{/info}}\nSuffix"), Syntax.XWIKI_2_1, Syntax.XDOMXML_CURRENT,
            printer);
        
        System.out.println(printer.toString());
    }
    
    @Test
    public void renderConfluence5() throws ComponentLookupException, ConversionException {
        InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream("/test-data/FAQ.xml"));
        converter.convert(reader, Syntax.XHTML_1_0, Syntax.XWIKI_2_1,
            printer);
        
        System.out.println(printer.toString());
    }
    
}
