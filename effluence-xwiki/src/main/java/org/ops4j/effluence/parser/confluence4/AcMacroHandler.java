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

package org.ops4j.effluence.parser.confluence4;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xwiki.rendering.internal.parser.wikimodel.XWikiGeneratorListener;
import org.xwiki.rendering.internal.parser.xhtml.XHTMLParser;
import org.xwiki.rendering.listener.MetaData;
import org.xwiki.rendering.renderer.PrintRenderer;
import org.xwiki.rendering.renderer.PrintRendererFactory;
import org.xwiki.rendering.renderer.printer.DefaultWikiPrinter;
import org.xwiki.rendering.wikimodel.WikiParameter;
import org.xwiki.rendering.wikimodel.WikiParameters;
import org.xwiki.rendering.wikimodel.impl.WikiScannerContext;
import org.xwiki.rendering.wikimodel.xhtml.handler.TagHandler;
import org.xwiki.rendering.wikimodel.xhtml.impl.XhtmlHandler.TagStack;
import org.xwiki.rendering.wikimodel.xhtml.impl.XhtmlHandler.TagStack.TagContext;


/**
 * @author Harald Wellmann
 *
 */
public class AcMacroHandler extends TagHandler {
    
    private static Logger log = LoggerFactory.getLogger(AcMacroHandler.class);
    private XHTMLParser parser;
    private PrintRendererFactory rendererFactory;
    
    
    /**
     * 
     */
    public AcMacroHandler(XHTMLParser parser, PrintRendererFactory rendererFactory) {
        super(false, false, true);
        this.parser = parser;
        this.rendererFactory = rendererFactory;
    }
    
    @Override
    protected void begin(TagContext context) {
        log.info("ac:macro begin");
        context.getTagStack().pushStackParameters();
        
        DefaultWikiPrinter printer = new DefaultWikiPrinter();

        PrintRenderer bodyRenderer = rendererFactory.createRenderer(printer);
        // Make sure to flush whatever the renderer implementation
        bodyRenderer.beginDocument(MetaData.EMPTY);

        XWikiGeneratorListener xwikiListener =
            parser.createXWikiGeneratorListener(bodyRenderer, null);
        context.getTagStack().pushScannerContext(new WikiScannerContext(xwikiListener));

        // Ensure we simulate a new document being parsed
        context.getScannerContext().beginDocument();
        
    }
    
    @Override
    protected void end(TagContext context) {
        TagStack tagStack = context.getTagStack();
        WikiParameters params = context.getParams();
        WikiParameter nameParam = params.getParameter("ac:name");
        if (nameParam != null) {
            params = params.remove("ac:name");
            String name = nameParam.getValue();
            if (name.equals("code")) {
                String defaultParameter = (String) tagStack.getStackParameter("default-parameter");
                if (defaultParameter != null) {
                    params = params.addParameter("language", defaultParameter);
                }
            }
        }
        
        @SuppressWarnings("unchecked")
        Map<String, String> parameters = (Map<String, String>) tagStack.getStackParameter("parameters");
        if (parameters != null) {
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                params = params.addParameter(entry.getKey(), entry.getValue());
            }
        }
                
        // Ensure we simulate a document parsing end
        context.getScannerContext().endDocument();

        WikiScannerContext scannerContext = tagStack.popScannerContext();

        XWikiGeneratorListener xwikiListener = (XWikiGeneratorListener) scannerContext.getfListener();
        PrintRenderer bodyRenderer = (PrintRenderer) xwikiListener.getListener();

        // Make sure to flush whatever the renderer implementation
        bodyRenderer.endDocument(MetaData.EMPTY);

        String body = bodyRenderer.getPrinter().toString();
        
        context.getScannerContext().onMacroBlock(nameParam.getValue(), params, body);
        context.getTagStack().popStackParameters();
    }
}
