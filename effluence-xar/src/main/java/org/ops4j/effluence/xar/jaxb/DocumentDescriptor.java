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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Harald Wellmann
 * 
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "xwikidoc")
public class DocumentDescriptor {

    private String web;
    private String name;
    private String language;
    private String defaultLanguage;
    private int translation;
    private String parent;
    private String creator;
    private String author;
    private String customClass;
    private String contentAuthor;
    private long creationDate;
    private long date;
    private long contentUpdateDate;
    private String version;
    private String title;
    private String template;
    private String defaultTemplate;
    private String validationScript;
    private String comment;
    private boolean minorEdit;
    private String syntaxId;
    private boolean hidden;
    private String content;

    /**
     * @return the web
     */
    public String getWeb() {
        return web;
    }

    /**
     * @param web
     *            the web to set
     */
    public void setWeb(String web) {
        this.web = web;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language
     *            the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the defaultLanguage
     */
    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    /**
     * @param defaultLanguage
     *            the defaultLanguage to set
     */
    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    /**
     * @return the translation
     */
    public int getTranslation() {
        return translation;
    }

    /**
     * @param translation
     *            the translation to set
     */
    public void setTranslation(int translation) {
        this.translation = translation;
    }

    /**
     * @return the parent
     */
    public String getParent() {
        return parent;
    }

    /**
     * @param parent
     *            the parent to set
     */
    public void setParent(String parent) {
        this.parent = parent;
    }

    /**
     * @return the creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * @param creator
     *            the creator to set
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author
     *            the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the customClass
     */
    public String getCustomClass() {
        return customClass;
    }

    /**
     * @param customClass
     *            the customClass to set
     */
    public void setCustomClass(String customClass) {
        this.customClass = customClass;
    }

    /**
     * @return the contentAuthor
     */
    public String getContentAuthor() {
        return contentAuthor;
    }

    /**
     * @param contentAuthor
     *            the contentAuthor to set
     */
    public void setContentAuthor(String contentAuthor) {
        this.contentAuthor = contentAuthor;
    }

    /**
     * @return the creationDate
     */
    public long getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate
     *            the creationDate to set
     */
    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return the date
     */
    public long getDate() {
        return date;
    }

    /**
     * @param date
     *            the date to set
     */
    public void setDate(long date) {
        this.date = date;
    }

    /**
     * @return the contentUpdateDate
     */
    public long getContentUpdateDate() {
        return contentUpdateDate;
    }

    /**
     * @param contentUpdateDate
     *            the contentUpdateDate to set
     */
    public void setContentUpdateDate(long contentUpdateDate) {
        this.contentUpdateDate = contentUpdateDate;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the template
     */
    public String getTemplate() {
        return template;
    }

    /**
     * @param template
     *            the template to set
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * @return the defaultTemplate
     */
    public String getDefaultTemplate() {
        return defaultTemplate;
    }

    /**
     * @param defaultTemplate
     *            the defaultTemplate to set
     */
    public void setDefaultTemplate(String defaultTemplate) {
        this.defaultTemplate = defaultTemplate;
    }

    /**
     * @return the validationScript
     */
    public String getValidationScript() {
        return validationScript;
    }

    /**
     * @param validationScript
     *            the validationScript to set
     */
    public void setValidationScript(String validationScript) {
        this.validationScript = validationScript;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment
     *            the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the minorEdit
     */
    public boolean isMinorEdit() {
        return minorEdit;
    }

    /**
     * @param minorEdit
     *            the minorEdit to set
     */
    public void setMinorEdit(boolean minorEdit) {
        this.minorEdit = minorEdit;
    }

    /**
     * @return the syntaxId
     */
    public String getSyntaxId() {
        return syntaxId;
    }

    /**
     * @param syntaxId
     *            the syntaxId to set
     */
    public void setSyntaxId(String syntaxId) {
        this.syntaxId = syntaxId;
    }

    /**
     * @return the hidden
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * @param hidden
     *            the hidden to set
     */
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

}
