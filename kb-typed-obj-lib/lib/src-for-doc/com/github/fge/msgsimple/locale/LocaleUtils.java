/*
 * Copyright (c) 2013, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.fge.msgsimple.locale;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * Utility methods for {@link Locale} management
 *
 * <p>This class provides two methods:</p>
 *
 * <ul>
 *     <li>{@link #parseLocale(String)} parses a string and builds a {@link
 *     Locale} object (strangely enough, there is no such method in the JDK!);
 *     </li>
 *     <li>{@link #getApplicable(Locale)} returns an ordered list of locales
 *     "applicable" to the given locale.</li>
 * </ul>
 *
 * <p>The {@link #getApplicable(Locale)} method emulates what the JDK's {@link
 * ResourceBundle} does when you look up a message in a locale; it returns an
 * ordered list from the most specific to the more general. For instance, given
 * the locale {@code "ja_JP_JP"}, it will generate the following list:</p>
 *
 * <ul>
 *     <li>{@code "ja_JP_JP"},</li>
 *     <li>{@code "ja_JP"},</li>
 *     <li>{@code "ja"},</li>
 *     <li>{@code ""} (the root locale, {@link Locale#ROOT}).</li>
 * </ul>
 */
public final class LocaleUtils
{
    private static final Pattern UNDERSCORE = Pattern.compile("_");

    private LocaleUtils()
    {
    }

    /**
     * Parse a string input as an argument and return a locale object
     *
     * <p>Three things to note:</p>
     *
     * <ul>
     *     <li>it is NOT checked whether the extracted language or country codes
     *     are actually registered to the ISO;</li>
     *     <li>all input strings with more than two underscores are deemed
     *     illegal;</li>
     *     <li>if the first component (the language) is empty, {@link
     *     Locale#ROOT} is returned.</li>
     * </ul>
     *
     * @see Locale
     *
     * @param input the input string
     * @throws NullPointerException input is null
     * @throws IllegalArgumentException input is malformed (see above)
     * @return a {@link Locale}
     */
    public static Locale parseLocale(final String input)
    {
        if (input == null)
            throw new NullPointerException("input cannot be null");

        if (input.isEmpty())
            return Locale.ROOT;

        /*
         * NOTE NOTE NOTE: in order for .split() to behave in a sane manner, we
         * MUST use the "multi-argument" version of .split() with a negative
         * argument. The no-argument version (this also stands for String's
         * .split()) will remove all empty strings from the end of the resulting
         * array up to the first non empty element.
         *
         * I don't know who designed this API, but he should either be given
         * the boot or killed[1].
         *
         * [1] choose the better option; hint: choose option 2
         */
        final String[] elements = UNDERSCORE.split(input, -1);
        final int len = elements.length;

        if (len > 3)
            throw new IllegalArgumentException("malformed input " + input);
        if (elements[0].isEmpty())
            return Locale.ROOT;

        switch (len) {
            case 1:
                return new Locale(elements[0]);
            case 2:
                return new Locale(elements[0], elements[1]);
            case 3:
                return new Locale(elements[0], elements[1], elements[2]);
            default:
                throw new IllegalStateException("How did I get there??");
        }
    }

    /**
     * Get a "decrementing" list of candidate locales for a given locale
     *
     * <p>The order of locale returned is from the more specific to the less
     * specific (the latter being {@link Locale#ROOT}).</p>
     *
     * @param target the locale
     * @return the list of applicable locales
     */
    public static Collection<Locale> getApplicable(final Locale target)
    {
        final String language = target.getLanguage();
        final String country = target.getCountry();
        final String variant = target.getVariant();

        final List<Locale> ret = new ArrayList<Locale>();
        ret.add(target);

        Locale locale;

        if (!variant.isEmpty()) {
            locale = new Locale(language, country);
            if (!locale.equals(Locale.ROOT))
                ret.add(locale);
        }

        if (!country.isEmpty()) {
            locale = new Locale(language);
            if (!locale.equals(Locale.ROOT))
                ret.add(locale);
        }

        if (!language.isEmpty())
            ret.add(Locale.ROOT);

        return ret;
    }
}
