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

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.xml.parsers.ParserConfigurationException;

import org.xwiki.rendering.wikimodel.xhtml.filter.AccumulationXMLFilter;
import org.xwiki.rendering.wikimodel.xhtml.filter.DTDXMLFilter;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xwiki.component.annotation.Component;
import org.xwiki.xml.EntityResolver;
import org.xwiki.xml.XMLReaderFactory;

/**
 * Creates XML Readers that have the following characteristics:
 * <ul>
 * <li>Use DTD caching when the underlying XML parser is Xerces</li>
 * <li>Ignore SAX callbacks when the parser parses the DTD</li>
 * <li>Accumulate onCharacters() calls since SAX parser may normally call this event several times.</li>
 * <li>Remove non-semantic white spaces where needed</li>
 * <li>Resolve DTDs locally to speed DTD loading/validation</li>
 * </ul>
 * 
 * @version $Id: 64f8a03e37c2675c5fcadb1a563e826ecf433a21 $
 * @since 2.1RC1
 */
@Component
@Named("xwiki-confluence4")
@Singleton
public class Confluence4XmlReaderFactory implements XMLReaderFactory
{
    /**
     * Used to create an optimized SAX XML Reader. In general SAX parsers don't cache DTD grammars and as a consequence
     * parsing a document with a grammar such as the XHTML DTD takes a lot more time than required.
     */
    @Inject
    private XMLReaderFactory xmlReaderFactory;

    /**
     * In order to speed up DTD loading/validation we use an entity resolver that can resolve DTDs locally.
     */
    @Inject
    protected EntityResolver entityResolver;

    @Override
    public XMLReader createXMLReader() throws SAXException, ParserConfigurationException
    {
        XMLReader xmlReader;

        try {
            // Use a performant XML Reader (which does DTD caching for Xerces)
            XMLReader xr = this.xmlReaderFactory.createXMLReader();

            // Ignore SAX callbacks when the parser parses the DTD
            DTDXMLFilter dtdFilter = new DTDXMLFilter(xr);

            // Add a XML Filter to accumulate onCharacters() calls since SAX
            // parser may call it several times.
            AccumulationXMLFilter accumulationFilter = new AccumulationXMLFilter(dtdFilter);

            // Add a XML Filter to remove non-semantic white spaces. We need to do that since all WikiModel
            // events contain only semantic information.
            Confluence4WhitespaceXmlFilter whitespaceFilter = new Confluence4WhitespaceXmlFilter(accumulationFilter);

            whitespaceFilter.setEntityResolver(this.entityResolver);

            xmlReader = whitespaceFilter;
        } catch (Exception e) {
            throw new SAXException("Failed to create XML reader", e);
        }

        return xmlReader;
    }
}
