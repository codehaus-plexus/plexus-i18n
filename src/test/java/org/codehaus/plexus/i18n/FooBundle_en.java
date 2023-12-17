package org.codehaus.plexus.i18n;

/*
 * Copyright 2001-2007 Codehaus Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.ListResourceBundle;

/**
 * An english resource bundle for use in testing.
 */
public class FooBundle_en extends ListResourceBundle {
    private static final Object[][] CONTENTS = {
        {"key1", "[en] value1"},
        {"key2", "[en] value2"},
        {"key3", "[en] value3"},
        {"key4", "[en] value4"}
    };

    protected Object[][] getContents() {
        return CONTENTS;
    }
}
