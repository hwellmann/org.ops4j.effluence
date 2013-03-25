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

import java.util.Date;

import javax.persistence.Embeddable;


/**
 * @author Harald Wellmann
 *
 */
@Embeddable
public class History {

    private Date creationDate;
    
    private String creatorName;
    
    private Date lastModificationDate;
    
    private String lastModifierName;

    
    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    
    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    
    /**
     * @return the creatorName
     */
    public String getCreatorName() {
        return creatorName;
    }

    
    /**
     * @param creatorName the creatorName to set
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    
    /**
     * @return the lastModificationDate
     */
    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    
    /**
     * @param lastModificationDate the lastModificationDate to set
     */
    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    
    /**
     * @return the lastModifierName
     */
    public String getLastModifierName() {
        return lastModifierName;
    }

    
    /**
     * @param lastModifierName the lastModifierName to set
     */
    public void setLastModifierName(String lastModifierName) {
        this.lastModifierName = lastModifierName;
    }
}
