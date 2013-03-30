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

package org.ops4j.effluence.parser.confluence4;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xwiki.rendering.wikimodel.WikiParameters;
import org.xwiki.rendering.wikimodel.WikiReference;
import org.xwiki.rendering.wikimodel.xhtml.handler.TagHandler;
import org.xwiki.rendering.wikimodel.xhtml.impl.XhtmlHandler.TagStack;
import org.xwiki.rendering.wikimodel.xhtml.impl.XhtmlHandler.TagStack.TagContext;


/**
 * @author Harald Wellmann
 *
 */
public class AcLinkHandler extends TagHandler {
    
    private static Logger log = LoggerFactory.getLogger(AcLinkHandler.class);
    
    
    /**
     * 
     */
    public AcLinkHandler() {
        super(false, false, true);
    }
    
    @Override
    protected void begin(TagContext context) {
        log.info("ac:link begin");
        context.getTagStack().pushStackParameters();
    }
    
    @Override
    protected void end(TagContext context) {
        WikiParameters params = context.getParams();
        log.info("params = {}", params);
        TagStack tagStack = context.getTagStack();
        String ref = (String) tagStack.getStackParameter("content-title");
        String spaceKey = (String) tagStack.getStackParameter("space-key");
        String linkBody = (String) tagStack.getStackParameter("link-body");
        if (StringUtils.isBlank(linkBody)) {
            linkBody = ref;
        }
        if (spaceKey != null) {
            ref = String.format("../%s/%s", spaceKey, ref);
        }
        WikiReference reference = new WikiReference(ref, linkBody);
        context.getScannerContext().onReference(reference);
        tagStack.popStackParameters();
    }


}
