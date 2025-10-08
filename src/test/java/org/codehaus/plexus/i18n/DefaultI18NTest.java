package org.codehaus.plexus.i18n;

/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2001-2002 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.codehaus.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Apache" and "Apache Software Foundation" and
 *    "Apache Turbine" must not be used to endorse or promote products
 *    derived from this software without prior written permission. For
 *    written permission, please contact codehaus@codehaus.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    "Apache Turbine", nor may "Apache" appear in their name, without
 *    prior written permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.codehaus.org/>.
 */

import javax.inject.Inject;

import java.util.Locale;

import org.codehaus.plexus.testing.PlexusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the API of the
 * {@link org.codehaus.plexus.i18n.I18N}.
 * <br>
 *
 * @author <a href="mailto:dlr@finemaltcoding.com">Daniel Rall</a>
 * @author <a href="mailto:jason@zenplex.com">Jason van Zyl</a>
 */
@PlexusTest
public class DefaultI18NTest {
    @Inject
    private I18N i18n;

    @BeforeEach
    protected void setUp() {
        /* Set an unsupported locale to default to ensure we do not get unexpected matches */
        Locale.setDefault(new Locale("jp"));
    }

    @Test
    public void testLocalization() {
        String s0 = i18n.getString(null, null, "key1");
        assertEquals("[] value1", s0, "Unable to retrieve localized text for locale: default");

        String s1 = i18n.getString(null, new Locale("en", "US"), "key2");
        assertEquals("[en_US] value2", s1, "Unable to retrieve localized text for locale: en-US");

        String s2 = i18n.getString("org.codehaus.plexus.i18n.BarBundle", new Locale("ko", "KR"), "key3");
        assertEquals("[ko] value3", s2, "Unable to retrieve localized text for locale: ko-KR");

        String s3 = i18n.getString("org.codehaus.plexus.i18n.BarBundle", new Locale("ja"), "key1");
        assertEquals("[] value1", s3, "Unable to fall back from non-existant locale: jp");

        String s4 = i18n.getString("org.codehaus.plexus.i18n.FooBundle", new Locale("fr"), "key3");
        assertEquals("[fr] value3", s4, "Unable to retrieve localized text for locale: fr");

        String s5 = i18n.getString("org.codehaus.plexus.i18n.FooBundle", new Locale("fr", "FR"), "key3");
        assertEquals("[fr] value3", s5, "Unable to retrieve localized text for locale: fr-FR");

        String s6 = i18n.getString("org.codehaus.plexus.i18n.i18n", null, "key1");
        assertEquals("[] value1", s6, "Unable to retrieve localized properties for locale: default");

        Locale old = Locale.getDefault();
        Locale.setDefault(Locale.FRENCH);
        try {
            String s7 = i18n.getString("org.codehaus.plexus.i18n.i18n", Locale.ENGLISH, "key1");
            assertEquals("[] value1", s7, "Should not fall back to default locale, use root bundle instead");

            String s8 = i18n.getString("org.codehaus.plexus.i18n.i18n", Locale.ITALIAN, "key1");
            assertEquals("[it] value1", s8, "Unable to retrieve localized properties for locale: it");

        } finally {
            Locale.setDefault(old);
        }
    }

    @Test
    public void testLocalizedMessagesWithFormatting() {
        // Format methods

        String s6 = i18n.format("org.codehaus.plexus.i18n.i18n", null, "thanks.message", "jason");
        assertEquals("Thanks jason!", s6);

        String s7 = i18n.format("org.codehaus.plexus.i18n.i18n", null, "thanks.message1", "jason", "van zyl");
        assertEquals("Thanks jason van zyl!", s7);

        String s8 = i18n.format(
                "org.codehaus.plexus.i18n.i18n", null, "thanks.message2", new Object[] {"jason", "van zyl"});

        assertEquals("Thanks jason van zyl!", s8);
    }

    @Test
    public void testLocalizedMessagesWithNonStandardLocale() {
        String s0 = i18n.getString("name", new Locale("xx"));
        assertEquals("plexus", s0);
    }

    @Test
    public void testNoFallbackToDefaultLocale() {
        // Save the current default locale
        Locale oldDefault = Locale.getDefault();

        try {
            // Set default locale to German
            Locale.setDefault(Locale.GERMAN);

            // Request a locale that doesn't have a bundle (Hebrew - Israel)
            // Expected: should get the root bundle with "[] value1"
            // Bug: currently gets the German bundle with "[de] value1"
            String result = i18n.getString("org.codehaus.plexus.i18n.i18n", new Locale("iw", "IL"), "key1");

            assertEquals("[] value1", result, "Should get root bundle, not default locale bundle");
        } finally {
            // Restore the original default locale
            Locale.setDefault(oldDefault);
        }
    }
}
