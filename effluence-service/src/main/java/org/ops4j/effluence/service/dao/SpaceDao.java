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

package org.ops4j.effluence.service.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.ops4j.effluence.model.Space;


/**
 * @author Harald Wellmann
 *
 */
@Stateless
public class SpaceDao {
    
    @PersistenceContext
    private EntityManager em;

    public Space findSpaceByKey(String key) {
        String jpql = "select s from Space s where s.key = :key";
        TypedQuery<Space> query = em.createQuery(jpql, Space.class);
        query.setParameter("key", key);
        try {
            return query.getSingleResult();
        }
        catch (NoResultException exc) {
            return null;
        }        
    }

}
