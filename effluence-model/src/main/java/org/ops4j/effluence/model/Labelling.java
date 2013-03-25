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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


/**
 * @author Harald Wellmann
 *
 */
@Entity
public class Labelling extends AbstractPersistentObject {
    
    @OneToOne
    private AbstractContent content;
    
    @Embedded
    private History history;
    
    private String user;
    
    @ManyToOne
    private Label label;

    
    /**
     * @return the content
     */
    public AbstractContent getContent() {
        return content;
    }

    
    /**
     * @param content the content to set
     */
    public void setContent(AbstractContent content) {
        this.content = content;
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

    
    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    
    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    
    /**
     * @return the label
     */
    public Label getLabel() {
        return label;
    }

    
    /**
     * @param label the label to set
     */
    public void setLabel(Label label) {
        this.label = label;
    }
}
