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

package org.ops4j.effluence.xar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.regex.Matcher;

import org.junit.Test;


/**
 * @author Harald Wellmann
 *
 */
public class ReplacementTest {
    
    @Test
    public void replaceCdata() {
        String text = "<![CDATA[\n\n]] >";
        // do we need to escape closing brackets?
        assertThat(text.replaceAll("]] >", "]]>"), is("<![CDATA[\n\n]]>"));
        assertThat(text.replaceAll("\\]\\] >", "]]>"), is("<![CDATA[\n\n]]>"));
    }
    
    @Test
    public void replaceDot() {
        String title = "A. B. C.";
        title = title.replaceAll("\\.", Matcher.quoteReplacement("\\."));
        assertThat(title, is("A\\. B\\. C\\."));
    }
}
