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

package org.ops4j.effluence.xar;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.ops4j.effluence.model.BodyContent;
import org.ops4j.effluence.model.History;
import org.ops4j.effluence.model.Page;
import org.ops4j.effluence.model.Space;
import org.ops4j.effluence.model.SpaceDescription;
import org.ops4j.effluence.service.dao.PageDao;
import org.ops4j.effluence.service.dao.SpaceDao;
import org.ops4j.effluence.xar.annotation.WikiSyntax;
import org.ops4j.effluence.xar.annotation.Xar;
import org.ops4j.effluence.xar.jaxb.DocumentDescriptor;
import org.ops4j.effluence.xar.jaxb.FileDescriptor;
import org.ops4j.effluence.xar.jaxb.PackageDescriptor;
import org.xwiki.rendering.block.XDOM;
import org.xwiki.rendering.parser.ParseException;
import org.xwiki.rendering.parser.Parser;
import org.xwiki.rendering.renderer.BlockRenderer;
import org.xwiki.rendering.renderer.printer.DefaultWikiPrinter;




/**
 * @author Harald Wellmann
 *
 */
@Stateless
public class XarExporter {
    
    @Inject
    private PageDao pageDao;
    
    @Inject
    private SpaceDao spaceDao;
    
    @Inject @Xar
    private Marshaller marshaller;
    
    @Inject @WikiSyntax("confluence/4.0")
    private Parser parser;
    
    @Inject @WikiSyntax("xwiki/2.1")
    private BlockRenderer renderer;

    private List<Page> pages;

    private File xarDir;

    private String spaceKey;

    private File spaceDir;
    
    public void exportXar(String spaceKey, File exportDir) {
        this.spaceKey = spaceKey;
        xarDir = new File(exportDir, "xar");
        spaceDir = new File(xarDir, spaceKey);
        spaceDir.mkdirs();

        pages = pageDao.findPagesBySpace(spaceKey);
        
        
        exportPackageDescriptor(spaceKey);
        
        for (Page page : pages) {
            exportPage(page);
        }        
    }

    /**
     * @param page
     */
    private void exportPage(Page page) {
        DocumentDescriptor doc = new DocumentDescriptor();
        doc.setWeb(spaceKey);
        doc.setName(page.getTitle());
        Page parent = page.getParent();
        if (parent != null) {
            doc.setParent(spaceKey + "." + page.getParent().getTitle());
        }
        History history = page.getHistory();
        doc.setCreator(history.getCreatorName());
        doc.setAuthor(history.getLastModifierName());
        doc.setContentAuthor(history.getLastModifierName());
        doc.setCreationDate(history.getCreationDate().getTime());
        doc.setDate(history.getLastModificationDate().getTime());
        doc.setContentUpdateDate(history.getLastModificationDate().getTime());
        doc.setVersion(page.getVersion() + ".1");
        doc.setTitle(page.getTitle());
        doc.setComment(page.getVersionComment());
        doc.setMinorEdit(false);
        
        List<BodyContent> contents = page.getBodyContents();
        if (contents != null && !contents.isEmpty()) {
            String confluenceContent = contents.get(0).getBody();
            String xwikiContent = convertContent(confluenceContent);
            doc.setContent(xwikiContent);
        }
        
        writeDocumentDescriptor(doc);
    }

    /**
     * @param confluenceContent
     * @return
     */
    private String convertContent(String confluenceContent) {
        try {
            XDOM xdom = parser.parse(new StringReader(confluenceContent));
            assertThat(xdom, is(notNullValue()));
            DefaultWikiPrinter printer = new DefaultWikiPrinter();
            renderer.render(xdom, printer);
            return printer.toString();
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void exportPackageDescriptor(String spaceKey) {
        String description = null;
        Space space = spaceDao.findSpaceByKey(spaceKey);
        SpaceDescription spaceDescription = space.getDescription();
        if (spaceDescription != null) {
            List<BodyContent> contents = spaceDescription.getBodyContents();
            if (contents != null && !contents.isEmpty()) {
                description = contents.get(0).getBody();
            }
        }
        
        
        PackageDescriptor pkg = new PackageDescriptor();
        pkg.setName(spaceKey);
        pkg.setDescription(description);
        pkg.setAuthor(space.getHistory().getCreatorName());
        pkg.setBackupPack(true);
        pkg.setPreserveVersion(true);
        
        List<FileDescriptor> files = pkg.getFile();
        for (Page page : pages) {
            String doc = String.format("%s.%s", spaceKey, page.getTitle());
            files.add(new FileDescriptor(doc));
        }
        
        writePackageDescriptor(pkg);
    }

    private void writePackageDescriptor(PackageDescriptor pkg) {
        File pkgFile = new File(xarDir, "package.xml");
        try (FileOutputStream os = new FileOutputStream(pkgFile)) {
            
            OutputStreamWriter writer = new OutputStreamWriter(os, "UTF-8");
            marshaller.marshal(pkg, writer);
            
        }
        catch (IOException | JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void writeDocumentDescriptor(DocumentDescriptor doc) {
        File pkgFile = new File(spaceDir, doc.getTitle() + ".xml");
        try (FileOutputStream os = new FileOutputStream(pkgFile)) {
            
            OutputStreamWriter writer = new OutputStreamWriter(os, "UTF-8");
            marshaller.marshal(doc, writer);
            
        }
        catch (IOException | JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
