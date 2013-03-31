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

package org.ops4j.effluence.xar.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.ops4j.effluence.xar.annotation.WikiSyntax;
import org.xwiki.component.embed.EmbeddableComponentManager;
import org.xwiki.component.manager.ComponentLookupException;
import org.xwiki.rendering.parser.Parser;
import org.xwiki.rendering.renderer.BlockRenderer;
import org.xwiki.rendering.syntax.Syntax;

/**
 * @author Harald Wellmann
 * 
 */
public class XwikiProducer {

    @Produces
    @ApplicationScoped
    public EmbeddableComponentManager createComponentManager() {
        EmbeddableComponentManager componentManager = new EmbeddableComponentManager();
        componentManager.initialize(this.getClass().getClassLoader());
        return componentManager;
    }

    @Produces
    @ApplicationScoped @WikiSyntax("confluence/4.0")
    public Parser createConfluence4Parser(EmbeddableComponentManager componentManager)
        throws ComponentLookupException {
        return componentManager.getInstance(Parser.class, "confluence/4.0");
    }

    @Produces
    @ApplicationScoped @WikiSyntax("xwiki/2.1")
    public Parser createXwiki21Renderer(EmbeddableComponentManager componentManager)
        throws ComponentLookupException {
        return componentManager.getInstance(BlockRenderer.class, Syntax.XWIKI_2_1.toIdString());
    }

}
