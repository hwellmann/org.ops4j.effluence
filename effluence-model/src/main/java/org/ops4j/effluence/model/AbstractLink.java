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

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;


/**
 * @author Harald Wellmann
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class AbstractLink extends AbstractPersistentObject {

    @ManyToOne
    private AbstractContent sourceContent;
    
    @Embedded
    private History history;

    
    /**
     * @return the sourceContent
     */
    public AbstractContent getSourceContent() {
        return sourceContent;
    }

    
    /**
     * @param sourceContent the sourceContent to set
     */
    public void setSourceContent(AbstractContent sourceContent) {
        this.sourceContent = sourceContent;
    }

    
    /**
     * @return the history
     */
    public History getHistory() {
        return history;
    }

    
    /**
     * @param history the history to set
     */
    public void setHistory(History history) {
        this.history = history;
    }
    
    
    
}
