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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ops4j.effluence.model.BodyContent;
import org.ops4j.effluence.model.Page;
import org.ops4j.effluence.model.Space;
import org.ops4j.effluence.service.dao.SpaceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Harald Wellmann
 *
 */
@Stateless
public class FileSystemExporter {
    
    private static Logger log = LoggerFactory.getLogger(FileSystemExporter.class);
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private SpaceDao spaceDao;
    
    public void exportSpace(String spaceKey, File outputDir) throws IOException {
        Space space = spaceDao.findSpaceByKey(spaceKey);
        Page homePage = space.getHomePage();
        File spaceDir = new File(outputDir, spaceKey);
        spaceDir.mkdirs();
        exportTree(homePage, spaceDir);
    }

    /**
     * @param homePage
     * @param outputDir
     * @throws IOException 
     */
    private void exportTree(Page page, File outputDir) throws IOException {
        String title = page.getTitle();
        File pageDir = new File(outputDir, title);
        pageDir.mkdirs();
        exportPage(page, pageDir);
        
        for (Page child : page.getChildren()) {
            exportTree(child, pageDir);
        }
    }

    /**
     * @param page
     * @param pageFile
     * @throws IOException 
     */
    private void exportPage(Page page, File pageDir) throws IOException {
        File pageFile = new File(pageDir, "page.xml");
        log.info("exporting {}", pageFile);
        BodyContent bodyContent = page.getBodyContents().get(0);
        bodyContent.getBody();
        
        FileOutputStream os = new FileOutputStream(pageFile);

        try (Writer writer = new OutputStreamWriter(os, "UTF-8")) {
            writer.write(bodyContent.getBody());           
        }
    }

}
