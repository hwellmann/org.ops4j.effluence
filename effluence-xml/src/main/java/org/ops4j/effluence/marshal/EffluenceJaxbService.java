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

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

import java.io.IOException;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.ops4j.effluence.jaxb.ERoot;
import org.xml.sax.SAXException;

/**
 * @author Harald Wellmann
 * 
 */
public class EffluenceJaxbService {

    /**
     * JAXB context for 24XML.
     */
    private JAXBContext context;

    /**
     * Schema for validation.
     */
    private Schema schema;

    /**
     * Loads schema and builds JAXB context.
     * 
     * @throws JAXBException
     */
    @PostConstruct
    public void initialize() {
        loadSchema();
        try {
            context = JAXBContext.newInstance(ERoot.class);
        }
        catch (JAXBException exc) {
            throw new RuntimeException(exc);
        }
    }

    /**
     * Loads schema from local resource.
     */
    private void loadSchema() {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
        URL schemaUrl = getClass().getResource("/xsd/effluence.xsd");
        try {
            schema = schemaFactory.newSchema(new StreamSource(schemaUrl.openStream()));
        }
        catch (SAXException | IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    /**
     * Creates a pretty-printing marshaller that does not validate.
     * 
     * @return JAXB marshaller
     * @throws JAXBException
     */
    public Marshaller createMarshaller() throws JAXBException {

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return marshaller;
    }

    /**
     * Creates a marshaller that does not validate.
     * 
     * @param formattedOutput
     *            use pretty-printing?
     * @return JAXB marshaller
     * @throws JAXBException
     */
    public Marshaller createMarshaller(boolean formattedOutput) throws JAXBException {

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formattedOutput);
        return marshaller;
    }

    /**
     * Creates a validating pretty-printing marshaller.
     * 
     * @return JAXB marshaller
     * @throws JAXBException
     */
    public Marshaller createValidatingMarshaller() throws JAXBException {

        Marshaller marshaller = createMarshaller();
        marshaller.setSchema(schema);
        return marshaller;
    }

    /**
     * Creates a validating marshaller.
     * 
     * @param formattedOutput
     *            use pretty-printing?
     * @return JAXB marshaller
     * @throws JAXBException
     */
    public Marshaller createValidatingMarshaller(boolean formattedOutput) throws JAXBException {

        Marshaller marshaller = createMarshaller(formattedOutput);
        marshaller.setSchema(schema);
        return marshaller;
    }

    /**
     * Creates a non-validating unmarshaller.
     * 
     * @return JAXB unmarshaller
     * @throws JAXBException
     */
    public Unmarshaller createUnmarshaller() throws JAXBException {

        return context.createUnmarshaller();
    }

    /**
     * Creates a validating unmarshaller.
     * 
     * @return JAXB unmarshaller
     * @throws JAXBException
     */
    public Unmarshaller createValidatingUnmarshaller() throws JAXBException {

        Unmarshaller unmarshaller = createUnmarshaller();
        unmarshaller.setSchema(schema);
        return unmarshaller;
    }

}
