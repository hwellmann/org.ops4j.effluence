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

package org.ops4j.effluence.metamodel;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Harald Wellmann
 *
 */
public class EntityClass {
    
    private Map<String, EntityProperty> properties;
    private Map<String, EntityCollection> collections;
    
    
    /**
     * 
     */
    public EntityClass() {
        this.properties = new HashMap<String, EntityProperty>();
        this.collections = new HashMap<String, EntityCollection>();
    }


    
    /**
     * @return the properties
     */
    public Map<String, EntityProperty> getProperties() {
        return properties;
    }


    
    /**
     * @param properties the properties to set
     */
    public void setProperties(Map<String, EntityProperty> properties) {
        this.properties = properties;
    }


    
    /**
     * @return the collections
     */
    public Map<String, EntityCollection> getCollections() {
        return collections;
    }


    
    /**
     * @param collections the collections to set
     */
    public void setCollections(Map<String, EntityCollection> collections) {
        this.collections = collections;
    }
    
    

}
