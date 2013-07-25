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

package com.github.fge.jsonschema.library.format;

import com.github.fge.jsonschema.format.FormatAttribute;
import com.github.fge.jsonschema.format.common.DateTimeAttribute;
import com.github.fge.jsonschema.format.common.EmailAttribute;
import com.github.fge.jsonschema.format.common.IPv6Attribute;
import com.github.fge.jsonschema.format.common.RegexAttribute;
import com.github.fge.jsonschema.format.common.URIAttribute;
import com.github.fge.jsonschema.library.Dictionary;
import com.github.fge.jsonschema.library.DictionaryBuilder;

/**
 * Format attributes common to draft v4 and v3
 */
public final class CommonFormatAttributesDictionary
{
    private static final Dictionary<FormatAttribute> DICTIONARY;

    private CommonFormatAttributesDictionary()
    {
    }

    static {
        final DictionaryBuilder<FormatAttribute> builder
            = Dictionary.newBuilder();

        builder.addAll(ExtraFormatsDictionary.get());

        String name;
        FormatAttribute attribute;

        name = "date-time";
        attribute = DateTimeAttribute.getInstance();
        builder.addEntry(name, attribute);

        name = "email";
        attribute = EmailAttribute.getInstance();
        builder.addEntry(name, attribute);

        name = "ipv6";
        attribute = IPv6Attribute.getInstance();
        builder.addEntry(name, attribute);

        name = "regex";
        attribute = RegexAttribute.getInstance();
        builder.addEntry(name, attribute);

        name = "uri";
        attribute = URIAttribute.getInstance();
        builder.addEntry(name, attribute);

        DICTIONARY = builder.freeze();
    }

    public static Dictionary<FormatAttribute> get()
    {
        return DICTIONARY;
    }
}
