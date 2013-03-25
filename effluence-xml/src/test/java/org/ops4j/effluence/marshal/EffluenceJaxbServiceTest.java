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

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.net.URL;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Before;
import org.junit.Test;
import org.ops4j.effluence.jaxb.EObject;
import org.ops4j.effluence.jaxb.ERoot;


/**
 * @author Harald Wellmann
 *
 */
public class EffluenceJaxbServiceTest {
    
    private EffluenceJaxbService jaxbService;

    @Before
    public void setUp() {
        jaxbService = new EffluenceJaxbService();
        jaxbService.initialize();
    }
    
    @Test
    public void unmarshal() throws JAXBException {
        Unmarshaller unmarshaller = jaxbService.createUnmarshaller();
        URL data = getClass().getResource("/test-data/entities1.xml");
        Object obj = unmarshaller.unmarshal(data);
        assertThat(obj, is(instanceOf(JAXBElement.class)));
        
        @SuppressWarnings("unchecked")
        JAXBElement<ERoot> rootElement = (JAXBElement<ERoot>) obj;
        ERoot root = rootElement.getValue();
        assertThat(root.getObject().size(), is(155));
        
        for (EObject object : root.getObject()) {
            System.out.println("class = " + object.getClazz());
        }
    }

}
