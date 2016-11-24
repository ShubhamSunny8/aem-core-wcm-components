/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 ~ Copyright 2016 Adobe Systems Incorporated
 ~
 ~ Licensed under the Apache License, Version 2.0 (the "License");
 ~ you may not use this file except in compliance with the License.
 ~ You may obtain a copy of the License at
 ~
 ~     http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~ Unless required by applicable law or agreed to in writing, software
 ~ distributed under the License is distributed on an "AS IS" BASIS,
 ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ~ See the License for the specific language governing permissions and
 ~ limitations under the License.
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
package apps.core.wcm.components.page.v1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.script.Bindings;

import org.junit.Before;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;

import apps.core.wcm.components.page.v1.page.Head;
import com.adobe.cq.sightly.WCMBindings;
import com.adobe.cq.wcm.core.components.testing.WCMUsePojoBaseTest;
import com.day.cq.wcm.api.designer.Design;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@PrepareForTest(Head.class)
public class HeadTest extends WCMUsePojoBaseTest<Head> {

    public static final String DESIGN_PATH = "/etc/designs/mysite";
    public static final String FN_ICO_FAVICON = "favicon.ico";
    public static final String FN_PNG_FAVICON = "favicon_32.png";
    public static final String FN_TOUCH_ICON_60 = "touch-icon_60.png";
    public static final String FN_TOUCH_ICON_76 = "touch-icon_76.png";
    public static final String FN_TOUCH_ICON_120 = "touch-icon_120.png";
    public static final String FN_TOUCH_ICON_152 = "touch-icon_152.png";

    static {
        TEST_CONTENT_ROOT = "/content/mysite";
        TEST_BASE = "/page";
    }

    public static final String TEMPLATED_PAGE = TEST_CONTENT_ROOT + "/templated-page";

    @Before
    public void setUp() {
        super.setUp();
        context.load().binaryFile(TEST_BASE + "/"+FN_ICO_FAVICON, DESIGN_PATH + "/"+FN_ICO_FAVICON);
        context.load().binaryFile(TEST_BASE + "/"+FN_PNG_FAVICON, DESIGN_PATH + "/"+FN_PNG_FAVICON);
        context.load().binaryFile(TEST_BASE + "/"+FN_TOUCH_ICON_60, DESIGN_PATH + "/"+FN_TOUCH_ICON_60);
        context.load().binaryFile(TEST_BASE + "/"+FN_TOUCH_ICON_76, DESIGN_PATH + "/"+FN_TOUCH_ICON_76);
        context.load().binaryFile(TEST_BASE + "/"+FN_TOUCH_ICON_120, DESIGN_PATH + "/"+FN_TOUCH_ICON_120);
        context.load().binaryFile(TEST_BASE + "/"+FN_TOUCH_ICON_152, DESIGN_PATH + "/"+FN_TOUCH_ICON_152);
        context.load().binaryFile(TEST_BASE + "/static.css", DESIGN_PATH + "/static.css");
        context.load().json(TEST_BASE + "/default-tags.json", "/etc/tags/default");
    }

    @Test
    public void testHead() {
        Head head = getSpiedObject();
        Bindings bindings = getResourceBackedBindings(TEMPLATED_PAGE);
        Design design = mock(Design.class);
        when(design.getPath()).thenReturn(DESIGN_PATH);
        bindings.put(WCMBindings.CURRENT_DESIGN, design);
        head.init(bindings);
        assertEquals("Templated Page", head.getTitle());
        assertEquals(DESIGN_PATH + ".css", head.getDesignPath());
        assertEquals(DESIGN_PATH + "/static.css", head.getStaticDesignPath());
        assertEquals(DESIGN_PATH + "/"+FN_ICO_FAVICON, head.getICOFavicon());
        assertEquals(DESIGN_PATH + "/"+FN_PNG_FAVICON, head.getPNGFavicon());
        assertEquals(DESIGN_PATH + "/"+FN_TOUCH_ICON_60, head.getTouchIcon60());
        assertEquals(DESIGN_PATH + "/"+FN_TOUCH_ICON_76, head.getTouchIcon76());
        assertEquals(DESIGN_PATH + "/"+FN_TOUCH_ICON_120, head.getTouchIcon120());
        assertEquals(DESIGN_PATH + "/"+FN_TOUCH_ICON_152, head.getTouchIcon152());
        String[] keywordsArray = head.getKeywords();
        assertEquals(3, keywordsArray.length);
        Set<String> keywords = new HashSet<>(keywordsArray.length);
        keywords.addAll(Arrays.asList(keywordsArray));
        assertTrue(keywords.contains("one") && keywords.contains("two") && keywords.contains("three"));
    }

}
