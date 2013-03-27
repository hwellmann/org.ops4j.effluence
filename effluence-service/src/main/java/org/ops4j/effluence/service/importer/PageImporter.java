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
import org.ops4j.effluence.model.Attachment;
import org.ops4j.effluence.model.BodyContent;
import org.ops4j.effluence.model.Comment;
import org.ops4j.effluence.model.ContentPermissionSet;
import org.ops4j.effluence.model.Labelling;
import org.ops4j.effluence.model.OutgoingLink;
import org.ops4j.effluence.model.Page;
import org.ops4j.effluence.model.ReferralLink;
import org.ops4j.effluence.model.Space;
import org.ops4j.effluence.model.TrackbackLink;

/**
 * @author Harald Wellmann
 * 
 */
@Stateless
@Local(ObjectImporter.class)
public class PageImporter extends AbstractObjectImporter<Page> {

    @Override
    public void importObject(EObject object) {
        em.clear();
        Page page = new Page();
        long id = object.getId().getValue();
        page.setId(id);
        log.debug("Page id = {}", id);
        setProperties(page, object);
        setCollections(page, object);
        em.merge(page);
    }

    private void setProperties(Page page, EObject object) {
        for (EProperty property : object.getProperty()) {
            String name = property.getName();
            if (name.equals("version")) {
                setVersion(page, property);
            }
            else if (name.equals("versionComment")) {
                setVersionComment(page, property);
            }
            else if (name.equals("lastModifierName")) {
                setLastModifierName(page, property);
            }
            else if (name.equals("lastModificationDate")) {
                setLastModificationDate(page, property);
            }
            else if (name.equals("position")) {
                setPosition(page, property);
            }
            else if (name.equals("creatorName")) {
                setCreatorName(page, property);
            }
            else if (name.equals("creationDate")) {
                setCreationDate(page, property);
            }
            else if (name.equals("title")) {
                setTitle(page, property);
            }
            else if (name.equals("originalVersion")) {
                setOriginalVersion(page, property);
            }
            else if (name.equals("contentStatus")) {
                setContentStatus(page, property);
            }
            else if (name.equals("parent")) {
                setParent(page, property);
            }
            else if (name.equals("space")) {
                setSpace(page, property);
            }
        }
    }

    /**
     * @param page
     * @param property
     */
    private void setVersion(Page page, EProperty property) {
        String value = (String) property.getContent().get(0);
        page.setVersion(Integer.parseInt(value));
    }

    /**
     * @param page
     * @param property
     */
    private void setVersionComment(Page page, EProperty property) {
        if (!property.getContent().isEmpty()) {
            String value = (String) property.getContent().get(0);
            page.setVersionComment(value);
        }
    }

    /**
     * @param page
     * @param property
     */
    private void setLastModifierName(Page page, EProperty property) {
        String value = (String) property.getContent().get(0);
        page.getHistory().setLastModifierName(value);
    }

    /**
     * @param page
     * @param property
     */
    private void setLastModificationDate(Page page, EProperty property) {
        String value = (String) property.getContent().get(0);
        try {
            Date date = modificationDateFormat.parse(value);
            page.getHistory().setLastModificationDate(date);
        }
        catch (ParseException e) {
            log.error("Cannot parse lastModificationDate: {}", value);
        }
    }

    /**
     * @param page
     * @param property
     */
    private void setPosition(Page page, EProperty property) {
        if (!property.getContent().isEmpty()) {
            String value = (String) property.getContent().get(0);
            page.setPosition(Integer.parseInt(value));
        }
    }

    /**
     * @param page
     * @param property
     */
    private void setCreatorName(Page page, EProperty property) {
        String value = (String) property.getContent().get(0);
        page.getHistory().setCreatorName(value);
    }

    /**
     * @param page
     * @param property
     */
    private void setCreationDate(Page page, EProperty property) {
        String value = (String) property.getContent().get(0);
        try {
            Date date = modificationDateFormat.parse(value);
            page.getHistory().setCreationDate(date);
        }
        catch (ParseException e) {
            log.error("Cannot parse creationDate: {}", value);
        }
    }

    /**
     * @param page
     * @param property
     */
    private void setTitle(Page page, EProperty property) {
        String value = (String) property.getContent().get(0);
        page.setTitle(value);
    }

    /**
     * @param page
     * @param property
     */
    private void setOriginalVersion(Page page, EProperty property) {
        Page originalVersion = findOrCreateAssociatedObject(property, Page.class);
        page.setOriginalVersion(originalVersion);
    }

    /**
     * @param page
     * @param property
     */
    private void setContentStatus(Page page, EProperty property) {
        String value = (String) property.getContent().get(0);
        page.setContentStatus(value);
    }

    /**
     * @param page
     * @param property
     */
    private void setParent(Page page, EProperty property) {
        Page parent = findOrCreateAssociatedObject(property, Page.class);
        page.setParent(parent);
    }

    /**
     * @param page
     * @param property
     */
    private void setSpace(Page page, EProperty property) {
        Space space = findOrCreateAssociatedObject(property, Space.class);
        page.setSpace(space);
    }

    /**
     * @param page
     * @param object
     */
    private void setCollections(Page page, EObject object) {
        for (ECollection collection : object.getCollection()) {
            String name = collection.getName();
            if (name.equals("outgoingLinks")) {
                setOutgoingLinks(page, collection);
            }
            else if (name.equals("trackbackLinks")) {
                setTrackbackLinks(page, collection);
            }
            else if (name.equals("referralLinks")) {
                setReferralLinks(page, collection);
            }
            else if (name.equals("labellings")) {
                setLabellings(page, collection);
            }
            else if (name.equals("contentPermissionSets")) {
                setContentPermissionSets(page, collection);
            }
            else if (name.equals("ancestors")) {
                setAncestors(page, collection);
            }
            else if (name.equals("children")) {
                setChildren(page, collection);
            }
            else if (name.equals("historicalVersions")) {
                setHistoricalVersions(page, collection);
            }
            else if (name.equals("attachments")) {
                setAttachments(page, collection);
            }
            else if (name.equals("comments")) {
                setComments(page, collection);
            }
            else if (name.equals("bodyContents")) {
                setBodyContents(page, collection);
            }
        }

    }

    /**
     * @param page
     * @param collection
     */
    private void setOutgoingLinks(Page page, ECollection collection) {
        List<OutgoingLink> outgoingLinks = new ArrayList<OutgoingLink>();
        for (EElement element : collection.getElement()) {
            addReferencedObject(outgoingLinks, element, OutgoingLink.class);
        }
        page.setOutgoingLinks(outgoingLinks);
    }

    /**
     * @param page
     * @param collection
     */
    private void setTrackbackLinks(Page page, ECollection collection) {
        List<TrackbackLink> trackbackLinks = new ArrayList<TrackbackLink>();
        for (EElement element : collection.getElement()) {
            addReferencedObject(trackbackLinks, element, TrackbackLink.class);
        }
        page.setTrackbackLinks(trackbackLinks);
    }

    /**
     * @param page
     * @param collection
     */
    private void setReferralLinks(Page page, ECollection collection) {
        List<ReferralLink> referralLinks = new ArrayList<ReferralLink>();
        for (EElement element : collection.getElement()) {
            addReferencedObject(referralLinks, element, ReferralLink.class);
        }
        page.setReferralLinks(referralLinks);
    }

    /**
     * @param page
     * @param collection
     */
    private void setLabellings(Page page, ECollection collection) {
        List<Labelling> labellings = new ArrayList<Labelling>();
        for (EElement element : collection.getElement()) {
            addReferencedObject(labellings, element, Labelling.class);
        }
        page.setLabellings(labellings);
    }

    /**
     * @param page
     * @param collection
     */
    private void setContentPermissionSets(Page page, ECollection collection) {
        List<ContentPermissionSet> contentPermissionSets = new ArrayList<ContentPermissionSet>();
        for (EElement element : collection.getElement()) {
            addReferencedObject(contentPermissionSets, element, ContentPermissionSet.class);
        }
        page.setContentPermissionSets(contentPermissionSets);
    }

    /**
     * @param page
     * @param collection
     */
    private void setAncestors(Page page, ECollection collection) {
        List<Page> ancestors = new ArrayList<Page>();
        for (EElement element : collection.getElement()) {
            addReferencedObject(ancestors, element, Page.class);
        }
        page.setAncestors(ancestors);
    }

    /**
     * @param page
     * @param collection
     */
    private void setChildren(Page page, ECollection collection) {
        List<Page> children = new ArrayList<Page>();
        for (EElement element : collection.getElement()) {
            addReferencedObject(children, element, Page.class);
        }
        page.setAncestors(children);
    }

    /**
     * @param page
     * @param collection
     */
    private void setHistoricalVersions(Page page, ECollection collection) {
        List<Page> historicalVersions = new ArrayList<Page>();
        for (EElement element : collection.getElement()) {
            addReferencedObject(historicalVersions, element, Page.class);
        }
        page.setAncestors(historicalVersions);
    }

    /**
     * @param page
     * @param collection
     */
    private void setAttachments(Page page, ECollection collection) {
        List<Attachment> attachments = new ArrayList<Attachment>();
        for (EElement element : collection.getElement()) {
            addReferencedObject(attachments, element, Attachment.class);
        }
        page.setAttachments(attachments);
    }

    /**
     * @param page
     * @param collection
     */
    private void setComments(Page page, ECollection collection) {
        List<Comment> comments = new ArrayList<Comment>();
        for (EElement element : collection.getElement()) {
            addReferencedObject(comments, element, Comment.class);
        }
        page.setComments(comments);
    }

    /**
     * @param page
     * @param collection
     */
    private void setBodyContents(Page page, ECollection collection) {
        List<BodyContent> bodyContents = new ArrayList<BodyContent>();
        for (EElement element : collection.getElement()) {
            addReferencedObject(bodyContents, element, BodyContent.class);
        }
        page.setBodyContents(bodyContents);
    }

    @Override
    public Class<Page> getEntityClass() {
        return Page.class;
    }
}
