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

package org.ops4j.effluence.service.importer;

import java.io.IOException;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.ops4j.effluence.marshal.StreamingEffluenceReader;


/**
 * @author Harald Wellmann
 *
 */
@ApplicationScoped
public class ConfluenceDumpImporter {
    
    @Inject
    private StreamingEffluenceReader reader;
    
    @Inject
    private ImportObjectHandler importObjectHandler;
    
    @PostConstruct
    public void init() {
        reader.setHandler(importObjectHandler);
    }
    
    public void importDump(URL url) {
        try {
            reader.readXml(url);
        }
        catch (JAXBException | IOException | XMLStreamException exc) {
            throw new RuntimeException(exc);
        }
    }

}
