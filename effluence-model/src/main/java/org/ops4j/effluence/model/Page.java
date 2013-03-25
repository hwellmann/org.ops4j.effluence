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

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;


/**
 * @author Harald Wellmann
 *
 */
@Entity
public class Page extends AbstractContent {
    
    private int position;
    
    @OneToOne
    private Page parent;
    
    @ManyToOne
    private Space space;

    @Transient
    private List<ContentPermissionSet> contentPermissionSets;

    @ManyToMany
    private List<Page> ancestors;
    
    @OneToMany(mappedBy = "parent")
    private List<Page> children;
    
    
    /**
     * 
     */
    public Page() {
        this.contentPermissionSets = new ArrayList<>();
        this.ancestors = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    
    /**
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    
    /**
     * @param position the position to set
     */
    public void setPosition(int position) {
        this.position = position;
    }

    
    /**
     * @return the parent
     */
    public Page getParent() {
        return parent;
    }

    
    /**
     * @param parent the parent to set
     */
    public void setParent(Page parent) {
        this.parent = parent;
    }

    
    /**
     * @return the contentPermissionSets
     */
    public List<ContentPermissionSet> getContentPermissionSets() {
        return contentPermissionSets;
    }

    
    /**
     * @param contentPermissionSets the contentPermissionSets to set
     */
    public void setContentPermissionSets(List<ContentPermissionSet> contentPermissionSets) {
        this.contentPermissionSets = contentPermissionSets;
    }

    
    /**
     * @return the ancestors
     */
    public List<Page> getAncestors() {
        return ancestors;
    }

    
    /**
     * @param ancestors the ancestors to set
     */
    public void setAncestors(List<Page> ancestors) {
        this.ancestors = ancestors;
    }

    
    /**
     * @return the children
     */
    public List<Page> getChildren() {
        return children;
    }

    
    /**
     * @param children the children to set
     */
    public void setChildren(List<Page> children) {
        this.children = children;
    }


    
    /**
     * @return the space
     */
    public Space getSpace() {
        return space;
    }


    
    /**
     * @param space the space to set
     */
    public void setSpace(Space space) {
        this.space = space;
    }
    
    
}
