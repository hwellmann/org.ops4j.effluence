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
import org.xwiki.rendering.wikimodel.xhtml.handler.TagHandler;
import org.xwiki.rendering.wikimodel.xhtml.impl.XhtmlHandler.TagStack.TagContext;


/**
 * @author Harald Wellmann
 *
 */
public class AcPlainTextBodyHandler extends TagHandler {
    
    private static Logger log = LoggerFactory.getLogger(AcPlainTextBodyHandler.class);
    
    
    /**
     * 
     */
    public AcPlainTextBodyHandler() {
        super(false, false, true);
    }
    
    @Override
    protected void begin(TagContext context) {
        log.info("ac:plain-text-body begin");
        setAccumulateContent(true);
    }
    
    @Override
    protected void end(TagContext context) {
        
        
        context.getTagStack().setStackParameter("plain-text-body", context.getContent());
        
    }


}
