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
import javax.persistence.Lob;
import javax.persistence.OneToOne;


/**
 * @author Harald Wellmann
 *
 */
@Entity
public class BodyContent extends AbstractPersistentObject {

    private int bodyType;
    
    @Lob
    private String body;
    
    @OneToOne
    private AbstractContent content;

    
    /**
     * @return the bodyType
     */
    public int getBodyType() {
        return bodyType;
    }

    
    /**
     * @param bodyType the bodyType to set
     */
    public void setBodyType(int bodyType) {
        this.bodyType = bodyType;
    }

    
    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    
    /**
     * @param body the body to set
     */
    public void setBody(String body) {
        this.body = body;
    }

    
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
    
    
}
