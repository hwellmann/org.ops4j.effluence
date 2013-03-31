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

package org.ops4j.effluence.xar.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.ops4j.effluence.xar.annotation.Xar;
import org.ops4j.effluence.xar.jaxb.DocumentDescriptor;
import org.ops4j.effluence.xar.jaxb.PackageDescriptor;


/**
 * @author Harald Wellmann
 *
 */
public class JaxbProducer {
    
    @Produces
    @ApplicationScoped 
    @Xar
    public JAXBContext createJaxbContext() throws JAXBException {
        return JAXBContext.newInstance(PackageDescriptor.class, DocumentDescriptor.class);       
    }

    @Produces
    @RequestScoped
    @Xar
    public Marshaller createMarshaller(@Xar JAXBContext context) throws JAXBException {
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return marshaller;        
    }

}
