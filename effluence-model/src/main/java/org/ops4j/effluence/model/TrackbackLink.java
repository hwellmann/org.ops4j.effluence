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
public class TrackbackLink extends ReferralLink {
    
    private String blogName;
    private String title;
    private String excerpt;
    
    /**
     * @return the blogName
     */
    public String getBlogName() {
        return blogName;
    }
    
    /**
     * @param blogName the blogName to set
     */
    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }
    
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * @return the excerpt
     */
    public String getExcerpt() {
        return excerpt;
    }
    
    /**
     * @param excerpt the excerpt to set
     */
    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }
}
