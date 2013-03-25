/*
 * Copyright 2011 Harald Wellmann
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.ops4j.effluence.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

/**
 * Provides a persistence context just for testing.
 * 
 * @author Harald Wellmann
 * 
 */
public class EntityManagerTest {

    public EntityManagerFactory getEntityManagerFactory() {
        Map<String, String> props = new HashMap<String, String>();
        props.put("javax.persistence.jdbc.driver", "org.apache.derby.jdbc.EmbeddedDriver");
        props.put("javax.persistence.jdbc.url", "jdbc:derby:target/effluence;create=true");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("effluence", props);
        return emf;
    }
    
    @Test
    public void createDatabase() {
        EntityManagerFactory emf = getEntityManagerFactory();
        emf.createEntityManager();
    }


}
