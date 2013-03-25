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

package org.ops4j.effluence.schema;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.ops4j.effluence.jaxb.ECollection;
import org.ops4j.effluence.jaxb.EObject;
import org.ops4j.effluence.jaxb.EProperty;
import org.ops4j.effluence.metamodel.EntityClass;
import org.ops4j.effluence.metamodel.EntityCollection;
import org.ops4j.effluence.metamodel.EntityProperty;


/**
 * @author Harald Wellmann
 *
 */
public class SchemaAnalyzer {
    
    private Map<String, EntityClass> entityClasses;
    
    
    /**
     * 
     */
    public SchemaAnalyzer() {
        this.entityClasses = new HashMap<String, EntityClass>();
    }
    
    public void analyzeObject(EObject object) {
        String className = object.getClazz();
        EntityClass entityClass = entityClasses.get(className);
        if (entityClass == null) {
            entityClass = buildEntityClass(object);
            entityClasses.put(className, entityClass);
        }
        else {
            augmentEntityClass(entityClass, object);
        }
        
    }

    /**
     * @param entityClass
     * @param object
     */
    private void augmentEntityClass(EntityClass entityClass, EObject object) {
        for (EProperty property : object.getProperty()) {
            Map<String, EntityProperty> properties = entityClass.getProperties();
            EntityProperty entityProperty = properties.get(property.getName());
            if (entityProperty == null) {
                properties.put(property.getName(), new EntityProperty());
            }
            
        }
        for (ECollection collection : object.getCollection()) {
            Map<String, EntityCollection> collections = entityClass.getCollections();
            EntityCollection entityCollection = collections.get(collection.getName());
            if (entityCollection == null) {
                collections.put(collection.getName(), new EntityCollection());
            }
        }
    }

    /**
     * @param object
     * @return
     */
    private EntityClass buildEntityClass(EObject object) {
        EntityClass entityClass = new EntityClass();
        for (EProperty property : object.getProperty()) {
            EntityProperty entityProperty = new EntityProperty();
            entityClass.getProperties().put(property.getName(), entityProperty);
        }
        for (ECollection collection : object.getCollection()) {
            EntityCollection entityCollection = new EntityCollection();
            entityClass.getCollections().put(collection.getName(), entityCollection);
        }
        return entityClass;
    }

    
    /**
     * @return the entityClasses
     */
    public Map<String, EntityClass> getEntityClasses() {
        return Collections.unmodifiableMap(entityClasses);
    }
    
    
}
