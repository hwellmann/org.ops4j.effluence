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

package org.ops4j.effluence.service.exporter;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.junit.PaxExam;


/**
 * @author Harald Wellmann
 *
 */
@RunWith(PaxExam.class)
public class FileSystemExporterTest {

    @Inject
    private FileSystemExporter exporter;
    
    @Test
    public void exportPaxExam() throws IOException  {
        File exportDir = new File("/tmp/effluence");
        exporter.exportSpace("PAXEXAM3", exportDir);
    }
}
