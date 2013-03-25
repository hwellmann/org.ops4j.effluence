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

package org.ops4j.effluence.marshal;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.EventFilter;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;


/**
 * @author Harald Wellmann
 *
 */
public class StreamingEffluenceReader {
    
    public static String ROOT_ELEMENT = "hibernate-generic";

    protected XMLEventReader filteredReader;



    @Inject
    private EffluenceJaxbService jaxbService;

    private ObjectHandler handler;

    private XMLEventReader eventReader;

    
    /**
     * @param jaxbService the jaxbService to set
     */
    public void setJaxbService(EffluenceJaxbService jaxbService) {
        this.jaxbService = jaxbService;
    }


    /**
     * Sets the handler to process the object read by the streaming parser.
     * 
     * @param handler
     */
    public void setHandler(ObjectHandler handler) {
        this.handler = handler;
    }

    /**
     * Reads a TMX stream from the given URL and produces JAXB objects passed to
     * the handler.
     * 
     * @param url URL of a TMX stream
     * @throws JAXBException
     * @throws IOException
     * @throws XMLStreamException
     */
    public void readXml(URL url) throws JAXBException, IOException,
            XMLStreamException {


        prepareXmlEventReader(url);

        Unmarshaller unmarshaller = jaxbService.createUnmarshaller();


        while (filteredReader.peek() != null) {
            Object elem = unmarshaller.unmarshal(eventReader);
            handler.handleElement(elem);
        }
    }
    

    protected void prepareXmlEventReader(URL url)
            throws FactoryConfigurationError, IOException, XMLStreamException {
        InputStream is = url.openStream();
        prepareXmlEventReader(is);
    }

    protected void prepareXmlEventReader(Reader reader)
            throws FactoryConfigurationError, XMLStreamException {
        
        XMLInputFactory factory = XMLInputFactory.newInstance();
        eventReader = factory.createXMLEventReader(reader);
        wrapEventReader(factory, eventReader);
    }


    protected void prepareXmlEventReader(InputStream is)
            throws FactoryConfigurationError, XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();

        eventReader = factory.createXMLEventReader(is);
        wrapEventReader(factory, eventReader);
    }

    /**
     * @param factory
     * @param eventReader
     * @throws XMLStreamException
     */
    private void wrapEventReader(XMLInputFactory factory,
            XMLEventReader eventReader) throws XMLStreamException {

        /*
         * Create a filter which allows us to skip to the next start element
         * event.
         */
        EventFilter filter = new EventFilter() {
            @Override
            public boolean accept(XMLEvent event) {
                return event.isStartElement();
            }
        };
        filteredReader = factory.createFilteredReader(eventReader, filter);

        // Skip to start of first element.
        StartElement e = (StartElement) filteredReader.nextEvent();
        if (!ROOT_ELEMENT.equals(e.getName().getLocalPart())) {
            throw new RuntimeException("expected <" + ROOT_ELEMENT + "> root element ");
        }
    }
}
