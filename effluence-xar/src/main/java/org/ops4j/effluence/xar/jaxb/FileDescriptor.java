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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * @author Harald Wellmann
 *
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class FileDescriptor {
    
    @XmlAttribute
    private int defaultAction;

    @XmlAttribute
    private String language = "";
    
    @XmlValue
    private String value;
    
    
    public FileDescriptor() {
        // TODO Auto-generated constructor stub
    }
    
    public FileDescriptor(String value) {
        this.value = value;
    }

    
    /**
     * @return the defaultAction
     */
    public int getDefaultAction() {
        return defaultAction;
    }

    
    /**
     * @param defaultAction the defaultAction to set
     */
    public void setDefaultAction(int defaultAction) {
        this.defaultAction = defaultAction;
    }

    
    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    
    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    
    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    
    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    

}
