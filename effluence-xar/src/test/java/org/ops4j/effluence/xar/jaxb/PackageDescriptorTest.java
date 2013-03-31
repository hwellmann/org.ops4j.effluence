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

package org.ops4j.effluence.xar.jaxb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Before;
import org.junit.Test;


/**
 * @author Harald Wellmann
 *
 */
public class PackageDescriptorTest {
    
    private Marshaller marshaller;
    
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Before
    public void setUp() throws JAXBException {
        
        JAXBContext context = JAXBContext.newInstance(PackageDescriptor.class, DocumentDescriptor.class);
        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    }
    
    @Test
    public void marshallPackageDescriptor() throws JAXBException {
        PackageDescriptor pkg = new PackageDescriptor();
        pkg.setName("MySpace");
        pkg.setDescription("foo");
        pkg.setAuthor("hwellmann");
        pkg.setBackupPack(true);
        pkg.setPreserveVersion(true);
        List<FileDescriptor> files = pkg.getFile();
        files.add(new FileDescriptor("MySpace.Page1"));
        files.add(new FileDescriptor("MySpace.Page2"));
        files.add(new FileDescriptor("MySpace.Page3"));
        
        marshaller.marshal(pkg, System.out);
    }
    
    @Test
    public void marshallDocumentDescriptor() throws JAXBException, ParseException {
        DocumentDescriptor doc = new DocumentDescriptor();
        
        doc.setWeb("MySpace");
        doc.setName("MyDoc");
        doc.setAuthor("hwellmann");
        Date date = dateFormat.parse("2013-03-31 11:48:00");
        doc.setCreationDate(date.getTime());
        doc.setDate(date.getTime());
        doc.setContentUpdateDate(date.getTime());
        doc.setContent("Line 1\nLine 2\nLine 3");
        
        marshaller.marshal(doc, System.out);
    }
    
}
