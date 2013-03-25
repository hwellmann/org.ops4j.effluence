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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.junit.PaxExam;


/**
 * @author Harald Wellmann
 *
 */
@RunWith(PaxExam.class)
public class ConfluenceDumpImporterTest {

    @Inject
    private ConfluenceDumpImporter importer;
    
    @Test
    public void importOps4jDump() throws MalformedURLException {
        URL data = new File("/home/hwellmann/Downloads/effluence/entities.xml").toURI().toURL();
        importer.importDump(data);
    }
}
