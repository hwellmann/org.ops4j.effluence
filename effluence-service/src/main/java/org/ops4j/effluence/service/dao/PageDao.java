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

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.ops4j.effluence.model.Page;


/**
 * @author Harald Wellmann
 *
 */
@Stateless
public class PageDao {
    
    @PersistenceContext
    private EntityManager em;

    public List<Page> findPagesBySpace(String key) {
        String jpql = "select p from Page p where p.space.key = :key order by p.title";
        TypedQuery<Page> query = em.createQuery(jpql, Page.class);
        query.setParameter("key", key);
        return query.getResultList();
    }

}
