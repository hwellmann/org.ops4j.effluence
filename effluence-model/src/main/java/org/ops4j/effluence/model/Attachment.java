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
import javax.persistence.ManyToOne;


/**
 * @author Harald Wellmann
 *
 */
@Entity
public class Attachment extends AbstractPersistentObject {

    
    private long fileSize;
    
    @ManyToOne
    private AbstractContent content;
    
    private String contentType; // MIME type 
    
    private String fileName;
    private String comment;
    
    /**
     * @return the fileSize
     */
    public long getFileSize() {
        return fileSize;
    }
    
    /**
     * @param fileSize the fileSize to set
     */
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
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
    
    /**
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }
    
    /**
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }
    
    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }
    
    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    
    
}
