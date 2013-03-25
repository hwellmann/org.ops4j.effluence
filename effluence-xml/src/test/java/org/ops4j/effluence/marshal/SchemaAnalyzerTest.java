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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;
import org.ops4j.effluence.jaxb.EObject;
import org.ops4j.effluence.jaxb.ERoot;
import org.ops4j.effluence.metamodel.EntityClass;
import org.ops4j.effluence.schema.SchemaAnalyzer;


/**
 * @author Harald Wellmann
 *
 */
public class SchemaAnalyzerTest {

    private EffluenceJaxbService jaxbService;
    private SchemaAnalyzer analyzer;
    private StreamingEffluenceReader streamingReader;

    @Before
    public void setUp() {
        jaxbService = new EffluenceJaxbService();
        jaxbService.initialize();
        
        analyzer = new SchemaAnalyzer();
        ObjectHandler objectHandler = new ObjectHandler() {
            @Override
            public void handleElement(Object elem) {
                @SuppressWarnings("unchecked")
                JAXBElement<EObject> jaxbElement = (JAXBElement<EObject>) elem;
                analyzer.analyzeObject(jaxbElement.getValue());
            }
        };
        
        streamingReader = new StreamingEffluenceReader();
        streamingReader.setJaxbService(jaxbService);
        streamingReader.setHandler(objectHandler);
        
    }
    
    @Test
    public void analyzeSmallSample() throws JAXBException {
        Unmarshaller unmarshaller = jaxbService.createUnmarshaller();
        URL data = getClass().getResource("/test-data/entities1.xml");

        @SuppressWarnings("unchecked")
        JAXBElement<ERoot> rootElement =  (JAXBElement<ERoot>) unmarshaller.unmarshal(data);
        ERoot root = rootElement.getValue();
        
        for (EObject object : root.getObject()) {
            analyzer.analyzeObject(object);
        }
        
        System.out.println(analyzer.getEntityClasses().keySet());
    }

    @Test
    public void analyzeCompleteDump() throws JAXBException, IOException, XMLStreamException {
        URL data = new File("/home/hwellmann/Downloads/effluence/entities.xml").toURI().toURL();
        streamingReader.readXml(data);
        
        SortedSet<String> classNames = new TreeSet<String>(analyzer.getEntityClasses().keySet());
        for (String className : classNames) {
            EntityClass entityClass = analyzer.getEntityClasses().get(className);
            System.out.println(className);
            System.out.println("    Properties  :" + entityClass.getProperties().keySet());
            System.out.println("    Collections :" + entityClass.getCollections().keySet());
        }
    }

}
