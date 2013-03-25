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
import java.net.URL;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;


/**
 * @author Harald Wellmann
 *
 */
public class StreamingEffluenceReaderTest {

    private EffluenceJaxbService jaxbService;
    private StreamingEffluenceReader streamingReader;

    @Before
    public void setUp() {
        jaxbService = new EffluenceJaxbService();
        jaxbService.initialize();
        
        streamingReader = new StreamingEffluenceReader();
        streamingReader.setJaxbService(jaxbService);
        streamingReader.setHandler(new ObjectHandler());
    }
    
    @Test
    public void readXml() throws JAXBException, IOException, XMLStreamException {
        URL data = getClass().getResource("/test-data/entities1.xml");
        streamingReader.readXml(data);
        
    }
}
