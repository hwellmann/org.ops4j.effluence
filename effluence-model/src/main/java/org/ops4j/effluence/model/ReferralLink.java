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
public class ReferralLink extends AbstractLink {

    private String url;
    
    private long viewCount;

    
    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    
    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    
    /**
     * @return the viewCount
     */
    public long getViewCount() {
        return viewCount;
    }

    
    /**
     * @param viewCount the viewCount to set
     */
    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }
}
