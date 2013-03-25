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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * @author Harald Wellmann
 *
 */
@Entity
@Table(name = "_space")
public class Space extends AbstractPersistentObject {
    
    private String name;
    
    private String key;
    
    
    private String spaceType;
    
    @Enumerated(EnumType.STRING)
    private SpaceStatus spaceStatus;
    
    @OneToOne
    private SpaceDescription description;

    @OneToOne
    private Page homePage;
    
    @Embedded
    private History history;
    
    @Transient
    private List<PageTemplate> pageTemplates;
    
    @Transient
    private List<SpacePermission> permissions;
    
    
    /**
     * 
     */
    public Space() {
        this.history = new History();
        this.pageTemplates = new ArrayList<>();
        this.permissions = new ArrayList<>();
    }

    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    
    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    
    /**
     * @return the spaceType
     */
    public String getSpaceType() {
        return spaceType;
    }

    
    /**
     * @param spaceType the spaceType to set
     */
    public void setSpaceType(String spaceType) {
        this.spaceType = spaceType;
    }

    
    /**
     * @return the spaceStatus
     */
    public SpaceStatus getSpaceStatus() {
        return spaceStatus;
    }

    
    /**
     * @param spaceStatus the spaceStatus to set
     */
    public void setSpaceStatus(SpaceStatus spaceStatus) {
        this.spaceStatus = spaceStatus;
    }

    
    /**
     * @return the description
     */
    public SpaceDescription getDescription() {
        return description;
    }

    
    /**
     * @param description the description to set
     */
    public void setDescription(SpaceDescription description) {
        this.description = description;
    }

    
    /**
     * @return the homePage
     */
    public Page getHomePage() {
        return homePage;
    }

    
    /**
     * @param homePage the homePage to set
     */
    public void setHomePage(Page homePage) {
        this.homePage = homePage;
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
     * @return the pageTemplates
     */
    public List<PageTemplate> getPageTemplates() {
        return pageTemplates;
    }

    
    /**
     * @param pageTemplates the pageTemplates to set
     */
    public void setPageTemplates(List<PageTemplate> pageTemplates) {
        this.pageTemplates = pageTemplates;
    }

    
    /**
     * @return the permissions
     */
    public List<SpacePermission> getPermissions() {
        return permissions;
    }

    
    /**
     * @param permissions the permissions to set
     */
    public void setPermissions(List<SpacePermission> permissions) {
        this.permissions = permissions;
    }
}
