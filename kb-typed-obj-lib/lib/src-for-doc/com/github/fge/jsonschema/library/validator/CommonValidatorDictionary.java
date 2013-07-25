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

package com.github.fge.jsonschema.library.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.keyword.validator.KeywordValidator;
import com.github.fge.jsonschema.keyword.validator.common.AdditionalItemsValidator;
import com.github.fge.jsonschema.keyword.validator.common.AdditionalPropertiesValidator;
import com.github.fge.jsonschema.keyword.validator.common.EnumValidator;
import com.github.fge.jsonschema.keyword.validator.common.MaxItemsValidator;
import com.github.fge.jsonschema.keyword.validator.common.MaxLengthValidator;
import com.github.fge.jsonschema.keyword.validator.common.MaximumValidator;
import com.github.fge.jsonschema.keyword.validator.common.MinItemsValidator;
import com.github.fge.jsonschema.keyword.validator.common.MinLengthValidator;
import com.github.fge.jsonschema.keyword.validator.common.MinimumValidator;
import com.github.fge.jsonschema.keyword.validator.common.PatternValidator;
import com.github.fge.jsonschema.keyword.validator.common.UniqueItemsValidator;
import com.github.fge.jsonschema.library.Dictionary;
import com.github.fge.jsonschema.library.DictionaryBuilder;

import java.lang.reflect.Constructor;

/**
 * Keyword validator constructors common to draft v4 and v3
 */
public final class CommonValidatorDictionary
{
    private static final Dictionary<Constructor<? extends KeywordValidator>>
        DICTIONARY;

    private CommonValidatorDictionary()
    {
    }

    public static Dictionary<Constructor<? extends KeywordValidator>> get()
    {
        return DICTIONARY;
    }

    static {
        final DictionaryBuilder<Constructor<? extends KeywordValidator>>
            builder = Dictionary.newBuilder();

        String keyword;
        Class<? extends KeywordValidator> c;

        /*
         * Arrays
         */
        keyword = "additionalItems";
        c = AdditionalItemsValidator.class;
        builder.addEntry(keyword, constructor(c));

        keyword = "minItems";
        c = MinItemsValidator.class;
        builder.addEntry(keyword, constructor(c));

        keyword = "maxItems";
        c = MaxItemsValidator.class;
        builder.addEntry(keyword, constructor(c));

        keyword = "uniqueItems";
        c = UniqueItemsValidator.class;
        builder.addEntry(keyword, constructor(c));

        /*
         * Numbers and integers
         */
        keyword = "minimum";
        c = MinimumValidator.class;
        builder.addEntry(keyword, constructor(c));

        keyword = "maximum";
        c = MaximumValidator.class;
        builder.addEntry(keyword, constructor(c));

        /*
         * Objects
         */
        keyword = "additionalProperties";
        c = AdditionalPropertiesValidator.class;
        builder.addEntry(keyword, constructor(c));

        /*
         * Strings
         */
        keyword = "minLength";
        c = MinLengthValidator.class;
        builder.addEntry(keyword, constructor(c));

        keyword = "maxLength";
        c = MaxLengthValidator.class;
        builder.addEntry(keyword, constructor(c));

        keyword = "pattern";
        c = PatternValidator.class;
        builder.addEntry(keyword, constructor(c));

        keyword = "enum";
        c = EnumValidator.class;
        builder.addEntry(keyword, constructor(c));

        DICTIONARY = builder.freeze();
    }

    private static Constructor<? extends KeywordValidator> constructor(
        final Class<? extends KeywordValidator> c)
    {
        try {
            return c.getConstructor(JsonNode.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("No appropriate constructor", e);
        }
    }
}
