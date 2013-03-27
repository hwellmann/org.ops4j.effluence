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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


/**
 * @author Harald Wellmann
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class AbstractContent extends AbstractPersistentObject {
    
    private String versionComment;
    
    @ManyToOne
    private AbstractContent originalVersion;
    
    @Embedded
    private History history;

    private String title;
    
    private String contentStatus;
    
    @OneToMany(mappedBy = "sourceContent")
    private List<OutgoingLink> outgoingLinks;
    
    @OneToMany(mappedBy = "originalVersion")
    private List<AbstractContent> historicalVersions;
    
    @OneToMany(mappedBy = "sourceContent")
    private List<TrackbackLink> trackbackLinks;
    
    @OneToMany(mappedBy = "content")
    private List<Labelling> labellings;
    
    @OneToMany(mappedBy = "sourceContent")
    private List<ReferralLink> referralLinks;
    
    @OneToMany(mappedBy = "content")
    private List<Attachment> attachments;
    
    @OneToMany(mappedBy = "owner")
    private List<Comment> comments;
    
    @OneToMany(mappedBy = "content")
    private List<BodyContent> bodyContents;
    
    
    /**
     * 
     */
    public AbstractContent() {
        this.history = new History();
        this.outgoingLinks = new ArrayList<>();
        this.trackbackLinks = new ArrayList<>();
        this.referralLinks = new ArrayList<>();
        this.labellings = new ArrayList<>();
        this.historicalVersions = new ArrayList<>();
        this.attachments = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.bodyContents = new ArrayList<>();
    }

    
    /**
     * @return the versionComment
     */
    public String getVersionComment() {
        return versionComment;
    }

    
    /**
     * @param versionComment the versionComment to set
     */
    public void setVersionComment(String versionComment) {
        this.versionComment = versionComment;
    }

    
    /**
     * @return the originalVersion
     */
    public AbstractContent getOriginalVersion() {
        return originalVersion;
    }

    
    /**
     * @param originalVersion the originalVersion to set
     */
    public void setOriginalVersion(AbstractContent originalVersion) {
        this.originalVersion = originalVersion;
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
     * @return the contentStatus
     */
    public String getContentStatus() {
        return contentStatus;
    }

    
    /**
     * @param contentStatus the contentStatus to set
     */
    public void setContentStatus(String contentStatus) {
        this.contentStatus = contentStatus;
    }

    
    /**
     * @return the outgoingLinks
     */
    public List<OutgoingLink> getOutgoingLinks() {
        return outgoingLinks;
    }

    
    /**
     * @param outgoingLinks the outgoingLinks to set
     */
    public void setOutgoingLinks(List<OutgoingLink> outgoingLinks) {
        this.outgoingLinks = outgoingLinks;
    }

    
    /**
     * @return the historicalVersions
     */
    public List<AbstractContent> getHistoricalVersions() {
        return historicalVersions;
    }

    
    /**
     * @param historicalVersions the historicalVersions to set
     */
    public void setHistoricalVersions(List<AbstractContent> historicalVersions) {
        this.historicalVersions = historicalVersions;
    }

    
    
    /**
     * @return the trackbackLinks
     */
    public List<TrackbackLink> getTrackbackLinks() {
        return trackbackLinks;
    }


    
    /**
     * @param trackbackLinks the trackbackLinks to set
     */
    public void setTrackbackLinks(List<TrackbackLink> trackbackLinks) {
        this.trackbackLinks = trackbackLinks;
    }


    /**
     * @return the labellings
     */
    public List<Labelling> getLabellings() {
        return labellings;
    }

    
    /**
     * @param labellings the labellings to set
     */
    public void setLabellings(List<Labelling> labellings) {
        this.labellings = labellings;
    }

    
    /**
     * @return the referralLinks
     */
    public List<ReferralLink> getReferralLinks() {
        return referralLinks;
    }

    
    /**
     * @param referralLinks the referralLinks to set
     */
    public void setReferralLinks(List<ReferralLink> referralLinks) {
        this.referralLinks = referralLinks;
    }

    
    /**
     * @return the attachments
     */
    public List<Attachment> getAttachments() {
        return attachments;
    }

    
    /**
     * @param attachments the attachments to set
     */
    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    
    /**
     * @return the comments
     */
    public List<Comment> getComments() {
        return comments;
    }

    
    /**
     * @param comments the comments to set
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    
    /**
     * @return the bodyContents
     */
    public List<BodyContent> getBodyContents() {
        return bodyContents;
    }

    
    /**
     * @param bodyContents the bodyContents to set
     */
    public void setBodyContents(List<BodyContent> bodyContents) {
        this.bodyContents = bodyContents;
    }
    
    
    
}
