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

package com.github.fge.jsonschema.report;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.github.fge.jackson.JacksonUtils;
import com.github.fge.jsonschema.util.AsJson;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

/**
 * {@link List}-based implementation of a {@link ProcessingReport}
 */
public final class ListProcessingReport
    extends AbstractProcessingReport
    implements AsJson
{
    private static final JsonNodeFactory FACTORY = JacksonUtils.nodeFactory();

    private final List<ProcessingMessage> messages = Lists.newArrayList();

    public ListProcessingReport(final LogLevel logLevel,
        final LogLevel exceptionThreshold)
    {
        super(logLevel, exceptionThreshold);
    }

    public ListProcessingReport(final LogLevel logLevel)
    {
        super(logLevel);
    }

    public ListProcessingReport()
    {
    }

    public ListProcessingReport(final ProcessingReport other)
    {
        this(other.getLogLevel(), other.getExceptionThreshold());
    }

    @Override
    public void log(final LogLevel level, final ProcessingMessage message)
    {
        messages.add(message);
    }

    @Override
    public JsonNode asJson()
    {
        final ArrayNode ret = FACTORY.arrayNode();
        for (final ProcessingMessage message: messages)
            ret.add(message.asJson());
        return ret;
    }

    @Override
    public Iterator<ProcessingMessage> iterator()
    {
        return Iterators.unmodifiableIterator(messages.iterator());
    }

}
