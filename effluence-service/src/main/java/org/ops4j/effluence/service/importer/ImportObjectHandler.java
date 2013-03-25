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

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;

import org.ops4j.effluence.jaxb.EObject;
import org.ops4j.effluence.marshal.ObjectHandler;
import org.ops4j.effluence.model.AbstractPersistentObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Harald Wellmann
 *
 */
@ApplicationScoped
public class ImportObjectHandler extends ObjectHandler {
    
    private static Logger log = LoggerFactory.getLogger(ImportObjectHandler.class);
    
    @SuppressWarnings("rawtypes")
    @Inject
    private Instance<ObjectImporter> objectImporters;
    
    private Map<String, ObjectImporter<? extends AbstractPersistentObject>> objectImporterMap;
    
    @PostConstruct
    public void createObjectImporterMap() {
        objectImporterMap = new HashMap<String, ObjectImporter<? extends AbstractPersistentObject>>();
        for (ObjectImporter<? extends AbstractPersistentObject> importer : objectImporters) {
            String className = importer.getEntityClass().getSimpleName();
            objectImporterMap.put(className, importer);
        }        
    }
    
    @Override
    public void handleElement(Object elem) {
        
        @SuppressWarnings("unchecked")
        JAXBElement<EObject> element = (JAXBElement<EObject>) elem;
        
        EObject object = element.getValue();
        String className = object.getClazz();
        ObjectImporter<? extends AbstractPersistentObject> importer = objectImporterMap.get(className);
        if (importer == null) {
            log.warn("no import handler for class {}", className);
            return;
        }
        importer.importObject(object);
    }
}
