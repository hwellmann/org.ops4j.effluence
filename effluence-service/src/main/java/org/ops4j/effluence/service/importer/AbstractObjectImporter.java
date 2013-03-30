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

import java.text.SimpleDateFormat;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBElement;

import org.ops4j.effluence.jaxb.EElement;
import org.ops4j.effluence.jaxb.EIdentity;
import org.ops4j.effluence.jaxb.EProperty;
import org.ops4j.effluence.model.AbstractPersistentObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Harald Wellmann
 *
 */
public abstract class AbstractObjectImporter<T extends AbstractPersistentObject> 
    implements ObjectImporter<T> {

    protected static Logger log = LoggerFactory.getLogger(PageImporter.class);

    @PersistenceContext
    protected EntityManager em;

    protected SimpleDateFormat modificationDateFormat = new SimpleDateFormat(
        "YYYY-MM-dd HH:mm:ss.SSS");

    /**
     * @param collection
     * @param element
     * @param class1
     */
    protected <U extends AbstractPersistentObject> void addReferencedObject(Collection<U> collection,
        EElement element, Class<U> entityClass) {
        U entity = findOrCreateAssociatedObject(element, entityClass);
        if (entity != null) {
            collection.add(entity);
        }
    }
    @SuppressWarnings("unchecked")
    protected <U extends AbstractPersistentObject> U findOrCreateAssociatedObject(EProperty property,
        Class<U> entityClass) {
        String clazz = property.getClazz();
        String entityName = entityClass.getSimpleName();
        String actualClassName = String.format("org.ops4j.effluence.model.%s", clazz);
        Class<U> actualClass = null;
        try {
            actualClass = (Class<U>) entityClass.getClassLoader().loadClass(actualClassName);
            if (!entityClass.isAssignableFrom(actualClass)) {
                log.error("expected class {}, got [{}]", entityName, clazz);
                return null;
            }
        }
        catch (ClassNotFoundException exc) {
            log.error("unknown entity class " + actualClassName);
            return null;
        }
        if (!(property.getContent().get(0) instanceof JAXBElement)) {

            log.error("expected <id>");
            return null;
        }

        JAXBElement<EIdentity> idElement = (JAXBElement<EIdentity>) property.getContent().get(0);
        
        EIdentity identity = idElement.getValue();
        return findOrCreateEntity(actualClass, entityName, identity);
    }

    protected <U extends AbstractPersistentObject> U findOrCreateEntity(Class<U> entityClass,
        String entityName, EIdentity identity) {
        try {
            long entityId = identity.getValue();
            U entity = em.find(entityClass, entityId);
            if (entity == null) {
                entity = entityClass.newInstance();                
                entity.setId(entityId);
                log.debug("    id = {}", entityId);
                em.persist(entity);
            }
            return entity;
        }
        catch (InstantiationException | IllegalAccessException e) {
            log.error("cannot create entity " + entityName, e);
            return null;
        }
    }

    protected <U extends AbstractPersistentObject> U findOrCreateAssociatedObject(EElement element,
        Class<U> entityClass) {
        String clazz = element.getClazz();
        String entityName = entityClass.getSimpleName();
        if (!entityName.equals(clazz)) {
            log.error("expected class {}, got [{}]", entityName, clazz);
            return null;
        }

        EIdentity identity = element.getId();
        return findOrCreateEntity(entityClass, entityName, identity);
    }
}
