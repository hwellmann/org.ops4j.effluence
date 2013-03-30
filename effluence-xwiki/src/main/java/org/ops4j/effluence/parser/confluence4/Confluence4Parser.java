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

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.component.manager.ComponentManager;
import org.xwiki.rendering.internal.parser.wikimodel.XWikiGeneratorListener;
import org.xwiki.rendering.internal.parser.xhtml.XHTMLParser;
import org.xwiki.rendering.internal.parser.xhtml.wikimodel.XHTMLXWikiGeneratorListener;
import org.xwiki.rendering.internal.parser.xhtml.wikimodel.XWikiCommentHandler;
import org.xwiki.rendering.internal.parser.xhtml.wikimodel.XWikiDivisionTagHandler;
import org.xwiki.rendering.internal.parser.xhtml.wikimodel.XWikiHeaderTagHandler;
import org.xwiki.rendering.internal.parser.xhtml.wikimodel.XWikiImageTagHandler;
import org.xwiki.rendering.internal.parser.xhtml.wikimodel.XWikiReferenceTagHandler;
import org.xwiki.rendering.internal.parser.xhtml.wikimodel.XWikiSpanTagHandler;
import org.xwiki.rendering.internal.parser.xhtml.wikimodel.XWikiTableDataTagHandler;
import org.xwiki.rendering.listener.Listener;
import org.xwiki.rendering.parser.ParseException;
import org.xwiki.rendering.parser.ResourceReferenceParser;
import org.xwiki.rendering.parser.StreamParser;
import org.xwiki.rendering.renderer.PrintRendererFactory;
import org.xwiki.rendering.syntax.Syntax;
import org.xwiki.rendering.syntax.SyntaxType;
import org.xwiki.rendering.util.IdGenerator;
import org.xwiki.rendering.wikimodel.IWikiParser;
import org.xwiki.rendering.wikimodel.xhtml.XhtmlParser;
import org.xwiki.rendering.wikimodel.xhtml.handler.TagHandler;
import org.xwiki.rendering.wikimodel.xhtml.handler.TeletypeTagHandler;
import org.xwiki.xml.XMLReaderFactory;


/**
 * Parser for Atlassian Confluence 4 syntax. Based on XWiki XHTML parser.
 *  
 * @author Harald Wellmann
 *
 */
@Component
@Named("confluence/4.0")
@Singleton
public class Confluence4Parser extends XHTMLParser {

    /**
     * The parser used for the link label parsing. For (x)html parsing, this will be an xwiki 2.0 parser, since it's
     * more convenient to pass link labels in xwiki syntax. See referred resource for more details.
     * 
     * @see XWikiCommentHandler#handleLinkCommentStop(String, org.xwiki.rendering.wikimodel.xhtml.impl.XhtmlHandler.TagStack)
     */
    @Inject
    @Named("xwiki/2.0")
    private StreamParser xwikiParser;

    /**
     * @see #getLinkReferenceParser()
     */
    @Inject
    @Named("link")
    private ResourceReferenceParser linkReferenceParser;

    /**
     * @see #getImageReferenceParser()
     */
    @Inject
    @Named("image")
    private ResourceReferenceParser imageReferenceParser;

    @Inject
    @Named("xwiki/2.1")
    private PrintRendererFactory xwikiSyntaxPrintRendererFactory;

    @Inject
    private ComponentManager componentManager;

    @Inject
    @Named("xhtmlmarker")
    private ResourceReferenceParser xhtmlMarkerResourceReferenceParser;

    /**
     * A special factory that create foolproof XML reader that have the following characteristics:
     * <ul>
     * <li>Use DTD caching when the underlying XML parser is Xerces</li>
     * <li>Ignore SAX callbacks when the parser parses the DTD</li>
     * <li>Accumulate onCharacters() calls since SAX parser may normally call this event several times.</li>
     * <li>Remove non-semantic white spaces where needed</li>
     * <li>Resolve DTDs locally to speed DTD loading/validation</li>
     * </ul>
     */
    @Inject
    @Named("xwiki-confluence4")
    private XMLReaderFactory xmlReaderFactory;

    @Override
    public Syntax getSyntax()
    {
        return new Syntax(SyntaxType.CONFLUENCE, "4.0");
    }

    @Override
    public StreamParser getLinkLabelParser()
    {
        return this.xwikiParser;
    }

    @Override
    public IWikiParser createWikiModelParser() throws ParseException
    {
        // Override some of the WikiModel XHTML parser tag handlers to introduce our own logic.
        Map<String, TagHandler> handlers = new HashMap<String, TagHandler>();
        TagHandler handler = new XWikiHeaderTagHandler();
        handlers.put("h1", handler);
        handlers.put("h2", handler);
        handlers.put("h3", handler);
        handlers.put("h4", handler);
        handlers.put("h5", handler);
        handlers.put("h6", handler);
        handlers.put("a", new XWikiReferenceTagHandler(this, this.xwikiSyntaxPrintRendererFactory));
        handlers.put("img", new XWikiImageTagHandler());
        handlers.put("span", new XWikiSpanTagHandler());
        handlers.put("div", new XWikiDivisionTagHandler());
        handlers.put("th", new XWikiTableDataTagHandler());

        // Additional XHTML tags
        handlers.put("code", new TeletypeTagHandler());
        
        // Tags with ac: prefix
        handlers.put("link", new AcLinkHandler());
        handlers.put("link-body", new AcLinkBodyHandler());

        // Tags with ri: prefix
        handlers.put("page", new RiPageHandler());
        
        
        XhtmlParser parser = new XhtmlParser();
        parser.setExtraHandlers(handlers);
        parser.setCommentHandler(new XWikiCommentHandler(this.componentManager, this,
            this.xwikiSyntaxPrintRendererFactory, this.xhtmlMarkerResourceReferenceParser));

        // Construct our own XML filter chain since we want to use our own Comment filter.
        try {
            parser.setXmlReader(this.xmlReaderFactory.createXMLReader());
        } catch (Exception e) {
            throw new ParseException("Failed to create XML reader", e);
        }

        return parser;
    }
    
    @Override
    public XWikiGeneratorListener createXWikiGeneratorListener(Listener listener, IdGenerator idGenerator)
    {
        return new XHTMLXWikiGeneratorListener(getLinkLabelParser(), listener, getLinkReferenceParser(),
            getImageReferenceParser(), this.plainRendererFactory, idGenerator, getSyntax());
    }
}
