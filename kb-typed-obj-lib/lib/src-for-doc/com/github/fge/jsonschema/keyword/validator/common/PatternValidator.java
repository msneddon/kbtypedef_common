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

package com.github.fge.jsonschema.keyword.validator.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.exceptions.ProcessingException;
import com.github.fge.jsonschema.keyword.validator.AbstractKeywordValidator;
import com.github.fge.jsonschema.processing.Processor;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.jsonschema.report.ProcessingReport;
import com.github.fge.jsonschema.util.RhinoHelper;
import com.github.fge.msgsimple.bundle.MessageBundle;

/**
 * Keyword validator for {@code pattern}
 *
 * @see RhinoHelper
 */
public final class PatternValidator
    extends AbstractKeywordValidator
{
    public PatternValidator(final JsonNode digest)
    {
        super("pattern");
    }

    @Override
    public void validate(final Processor<FullData, FullData> processor,
        final ProcessingReport report, final MessageBundle bundle,
        final FullData data)
        throws ProcessingException
    {
        final String regex = data.getSchema().getNode().get(keyword)
            .textValue();
        final String value = data.getInstance().getNode().textValue();
        if (!RhinoHelper.regMatch(regex, value))
            report.error(newMsg(data, bundle, "err.common.pattern.noMatch")
                .putArgument("regex", regex).putArgument("string", value));
    }

    @Override
    public String toString()
    {
        return keyword;
    }
}
