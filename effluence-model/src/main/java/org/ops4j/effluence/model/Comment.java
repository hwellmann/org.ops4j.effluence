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

import javax.persistence.Entity;
import javax.persistence.OneToOne;


/**
 * @author Harald Wellmann
 *
 */
@Entity
public class Comment extends AbstractContent {

    @OneToOne
    private AbstractContent owner;
    
    @OneToOne
    private Comment parent;

    
    /**
     * @return the owner
     */
    public AbstractContent getOwner() {
        return owner;
    }

    
    /**
     * @param owner the owner to set
     */
    public void setOwner(AbstractContent owner) {
        this.owner = owner;
    }

    
    /**
     * @return the parent
     */
    public Comment getParent() {
        return parent;
    }

    
    /**
     * @param parent the parent to set
     */
    public void setParent(Comment parent) {
        this.parent = parent;
    }
    
}
