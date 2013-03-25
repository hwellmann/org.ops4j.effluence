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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.ops4j.effluence.jaxb.ECollection;
import org.ops4j.effluence.jaxb.EElement;
import org.ops4j.effluence.jaxb.EObject;
import org.ops4j.effluence.jaxb.EProperty;
import org.ops4j.effluence.model.Page;
import org.ops4j.effluence.model.PageTemplate;
import org.ops4j.effluence.model.Space;
import org.ops4j.effluence.model.SpaceDescription;
import org.ops4j.effluence.model.SpacePermission;
import org.ops4j.effluence.model.SpaceStatus;

/**
 * @author Harald Wellmann
 * 
 */
@Stateless
@Local(ObjectImporter.class)
public class SpaceImporter extends AbstractObjectImporter<Space> {

    @Override
    public void importObject(EObject object) {
        Space space = new Space();
        space.setId(object.getId().getValue());
        setProperties(space, object);
        setCollections(space, object);
        em.merge(space);
    }

    private void setProperties(Space space, EObject object) {
        for (EProperty property : object.getProperty()) {
            String name = property.getName();
            if (name.equals("version")) {
                setVersion(space, property);
            }
            else if (name.equals("lastModifierName")) {
                setLastModifierName(space, property);
            }
            else if (name.equals("lastModificationDate")) {
                setLastModificationDate(space, property);
            }
            else if (name.equals("creatorName")) {
                setCreatorName(space, property);
            }
            else if (name.equals("creationDate")) {
                setCreationDate(space, property);
            }
            else if (name.equals("description")) {
                setDescription(space, property);
            }
            else if (name.equals("homePage")) {
                setHomePage(space, property);
            }
            else if (name.equals("key")) {
                setKey(space, property);
            }
            else if (name.equals("name")) {
                setName(space, property);
            }
            else if (name.equals("spaceStatus")) {
                setSpaceStatus(space, property);
            }
            else if (name.equals("spaceType")) {
                setSpaceType(space, property);
            }
        }
    }

    /**
     * @param space
     * @param property
     */
    private void setDescription(Space space, EProperty property) {
        SpaceDescription description = findOrCreateAssociatedObject(property,
            SpaceDescription.class);
        space.setDescription(description);
    }

    /**
     * @param space
     * @param property
     */
    private void setHomePage(Space space, EProperty property) {
        Page homePage = findOrCreateAssociatedObject(property, Page.class);
        space.setHomePage(homePage);
    }

    /**
     * @param space
     * @param property
     */
    private void setKey(Space space, EProperty property) {
        String value = (String) property.getContent().get(0);
        space.setKey(value);
    }

    /**
     * @param space
     * @param property
     */
    private void setName(Space space, EProperty property) {
        String value = (String) property.getContent().get(0);
        space.setName(value);
    }

    /**
     * @param space
     * @param property
     */
    private void setSpaceStatus(Space space, EProperty property) {
        String value = (String) property.getContent().get(0);
        space.setSpaceStatus(SpaceStatus.valueOf(value));
    }

    /**
     * @param space
     * @param property
     */
    private void setSpaceType(Space space, EProperty property) {
        String value = (String) property.getContent().get(0);
        space.setSpaceType(value);
    }

    /**
     * @param space
     * @param property
     */
    private void setVersion(Space space, EProperty property) {
        String value = (String) property.getContent().get(0);
        space.setVersion(Integer.parseInt(value));
    }

    /**
     * @param space
     * @param property
     */
    private void setLastModifierName(Space space, EProperty property) {
        String value = (String) property.getContent().get(0);
        space.getHistory().setLastModifierName(value);
    }

    /**
     * @param space
     * @param property
     */
    private void setLastModificationDate(Space space, EProperty property) {
        String value = (String) property.getContent().get(0);
        try {
            Date date = modificationDateFormat.parse(value);
            space.getHistory().setLastModificationDate(date);
        }
        catch (ParseException e) {
            log.error("Cannot parse lastModificationDate: {}", value);
        }
    }

    /**
     * @param space
     * @param property
     */
    private void setCreatorName(Space space, EProperty property) {
        if (!property.getContent().isEmpty()) {
            String value = (String) property.getContent().get(0);
            space.getHistory().setCreatorName(value);
        }
    }

    /**
     * @param space
     * @param property
     */
    private void setCreationDate(Space space, EProperty property) {
        String value = (String) property.getContent().get(0);
        try {
            Date date = modificationDateFormat.parse(value);
            space.getHistory().setCreationDate(date);
        }
        catch (ParseException e) {
            log.error("Cannot parse creationDate: {}", value);
        }
    }

    /**
     * @param space
     * @param object
     */
    private void setCollections(Space space, EObject object) {
        for (ECollection collection : object.getCollection()) {
            String name = collection.getName();
            if (name.equals("pageTemplates")) {
                setPageTemplates(space, collection);
            }
            else if (name.equals("permissions")) {
                setPermissions(space, collection);
            }
        }

    }

    /**
     * @param space
     * @param collection
     */
    private void setPageTemplates(Space space, ECollection collection) {
        List<PageTemplate> pageTemplates = new ArrayList<PageTemplate>();
        for (EElement element : collection.getElement()) {
            addReferencedObject(pageTemplates, element, PageTemplate.class);
        }
        space.setPageTemplates(pageTemplates);
    }

    /**
     * @param space
     * @param collection
     */
    private void setPermissions(Space space, ECollection collection) {
        List<SpacePermission> permissions = new ArrayList<SpacePermission>();
        for (EElement element : collection.getElement()) {
            addReferencedObject(permissions, element, SpacePermission.class);
        }
        space.setPermissions(permissions);
    }

    @Override
    public Class<Space> getEntityClass() {
        return Space.class;
    }
}
