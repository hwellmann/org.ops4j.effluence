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

package org.ops4j.effluence.xar.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * @author Harald Wellmann
 *
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "package")
public class PackageDescriptor {
    
    private String infos;
    
    private String name;
    
    private String description;
    
    private String licence;
    
    private String author;
    
    private String version;
    
    private boolean backupPack;

    private boolean preserveVersion;
    
    private List<FileDescriptor> file = new ArrayList<FileDescriptor>();

    
    /**
     * @return the infos
     */
    public String getInfos() {
        return infos;
    }

    
    /**
     * @param infos the infos to set
     */
    public void setInfos(String infos) {
        this.infos = infos;
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    
    /**
     * @return the licence
     */
    public String getLicence() {
        return licence;
    }

    
    /**
     * @param licence the licence to set
     */
    public void setLicence(String licence) {
        this.licence = licence;
    }

    
    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    
    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    
    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    
    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    
    /**
     * @return the backupPack
     */
    public boolean isBackupPack() {
        return backupPack;
    }

    
    /**
     * @param backupPack the backupPack to set
     */
    public void setBackupPack(boolean backupPack) {
        this.backupPack = backupPack;
    }

    
    /**
     * @return the preserveVersion
     */
    public boolean isPreserveVersion() {
        return preserveVersion;
    }

    
    /**
     * @param preserveVersion the preserveVersion to set
     */
    public void setPreserveVersion(boolean preserveVersion) {
        this.preserveVersion = preserveVersion;
    }

    
    /**
     * @return the file
     */
    public List<FileDescriptor> getFile() {
        return file;
    }
}
