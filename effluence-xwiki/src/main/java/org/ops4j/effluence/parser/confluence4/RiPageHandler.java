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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xwiki.rendering.wikimodel.WikiParameter;
import org.xwiki.rendering.wikimodel.WikiParameters;
import org.xwiki.rendering.wikimodel.xhtml.handler.TagHandler;
import org.xwiki.rendering.wikimodel.xhtml.impl.XhtmlHandler.TagStack.TagContext;


/**
 * @author Harald Wellmann
 *
 */
public class RiPageHandler extends TagHandler {
    
    private static Logger log = LoggerFactory.getLogger(RiPageHandler.class);
    
    
    /**
     * 
     */
    public RiPageHandler() {
        super(false, false, false);
    }
    
    @Override
    protected void begin(TagContext context) {
        log.info("ri:page begin");
        WikiParameters params = context.getParams();
        log.info("params = {}", params);
    }
    
    @Override
    protected void end(TagContext context) {
        
        
        WikiParameters params = context.getParams();
        log.info("params = {}", params);
        WikiParameter contentTitle = params.getParameter("ri:content-title");
        WikiParameter spaceKey = params.getParameter("ri:space-key");
//        String content = context.getContent();
//        if (StringUtils.isBlank(content)) {
//            content = contentTitle.getValue();
//        }
        StringBuilder ref = new StringBuilder();
        if (spaceKey != null) {
            context.getTagStack().setStackParameter("space-key", spaceKey.getValue());
            ref.append("../");
            ref.append(spaceKey.getValue());
            ref.append("/");
        }
        ref.append(contentTitle.getValue());
        context.getTagStack().setStackParameter("content-title", contentTitle.getValue());
        
//        WikiReference reference = new WikiReference(contentTitle.getValue(), content);
//        context.getScannerContext().onReference(reference);
    }


}
