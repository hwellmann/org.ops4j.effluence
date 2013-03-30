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

package org.ops4j.effluence.service.importer;

import java.util.concurrent.atomic.AtomicInteger;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.ops4j.effluence.jaxb.EObject;
import org.ops4j.effluence.jaxb.EProperty;
import org.ops4j.effluence.model.AbstractContent;
import org.ops4j.effluence.model.BodyContent;

/**
 * @author Harald Wellmann
 * 
 */
@Stateless
@Local(ObjectImporter.class)
public class BodyContentImporter extends AbstractObjectImporter<BodyContent> {

    private static AtomicInteger count = new AtomicInteger();

    @Override
    public void importObject(EObject object) {
        BodyContent bodyContent = new BodyContent();
        bodyContent.setId(object.getId().getValue());
        setProperties(bodyContent, object);
        em.merge(bodyContent);
        int numObjects = count.incrementAndGet();
        if (numObjects % 100 == 0) {
            log.info("{} bodyContents", numObjects);
        }
    }

    private void setProperties(BodyContent bodyContent, EObject object) {
        for (EProperty property : object.getProperty()) {
            String name = property.getName();
            if (name.equals("version")) {
                setVersion(bodyContent, property);
            }
            else if (name.equals("body")) {
                setBody(bodyContent, property);
            }
            else if (name.equals("bodyType")) {
                setBodyType(bodyContent, property);
            }
            else if (name.equals("content")) {
                setContent(bodyContent, property);
            }
        }
    }

    /**
     * @param bodyContent
     * @param property
     */
    private void setContent(BodyContent bodyContent, EProperty property) {
        AbstractContent content = findOrCreateAssociatedObject(property, AbstractContent.class);
        bodyContent.setContent(content);
    }

    /**
     * @param bodyContent
     * @param property
     */
    private void setBodyType(BodyContent bodyContent, EProperty property) {
        String value = (String) property.getContent().get(0);
        bodyContent.setBodyType(Integer.parseInt(value));
    }

    /**
     * @param bodyContent
     * @param property
     */
    private void setVersion(BodyContent bodyContent, EProperty property) {
        String value = (String) property.getContent().get(0);
        bodyContent.setVersion(Integer.parseInt(value));
    }

    /**
     * @param bodyContent
     * @param property
     */
    private void setBody(BodyContent bodyContent, EProperty property) {
        if (property.getContent() == null || property.getContent().isEmpty()) {
            return;
        }
        String value = (String) property.getContent().get(0);
        bodyContent.setBody(value);
    }

    @Override
    public Class<BodyContent> getEntityClass() {
        return BodyContent.class;
    }
}
