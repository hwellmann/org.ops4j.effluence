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

package org.ops4j.effluence.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;


/**
 * @author Harald Wellmann
 *
 */
@MappedSuperclass
public abstract class AbstractPersistentObject {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    //@Version
    private long version;

    
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    
    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    
    /**
     * @return the version
     */
    public long getVersion() {
        return version;
    }

    
    /**
     * @param version the version to set
     */
    public void setVersion(long version) {
        this.version = version;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AbstractPersistentObject other = (AbstractPersistentObject) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }
    
}
