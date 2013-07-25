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

package com.github.fge.jsonschema.keyword.validator.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.keyword.validator.AbstractKeywordValidator;

/**
 * Helper validator class for keywords whose value is a positive integer
 */
public abstract class PositiveIntegerValidator
    extends AbstractKeywordValidator
{
    protected final int intValue;

    protected PositiveIntegerValidator(final String keyword,
        final JsonNode digest)
    {
        super(keyword);
        intValue = digest.get(keyword).intValue();
    }

    @Override
    public final String toString()
    {
        return keyword + ": " + intValue;
    }
}
