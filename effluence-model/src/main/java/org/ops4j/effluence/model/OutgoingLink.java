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


/**
 * @author Harald Wellmann
 *
 */
@Entity
public class OutgoingLink extends AbstractLink {

    private String destinationSpaceKey;
    
    private String destinationPageTitle;

    
    /**
     * @return the destinationSpaceKey
     */
    public String getDestinationSpaceKey() {
        return destinationSpaceKey;
    }

    
    /**
     * @param destinationSpaceKey the destinationSpaceKey to set
     */
    public void setDestinationSpaceKey(String destinationSpaceKey) {
        this.destinationSpaceKey = destinationSpaceKey;
    }

    
    /**
     * @return the destinationPageTitle
     */
    public String getDestinationPageTitle() {
        return destinationPageTitle;
    }

    
    /**
     * @param destinationPageTitle the destinationPageTitle to set
     */
    public void setDestinationPageTitle(String destinationPageTitle) {
        this.destinationPageTitle = destinationPageTitle;
    }
}
