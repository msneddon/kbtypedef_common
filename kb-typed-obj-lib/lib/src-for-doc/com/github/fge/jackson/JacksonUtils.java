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

package com.github.fge.jackson;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.google.common.collect.Maps;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/**
 * Utility class for Jackson
 *
 * <p>This class provides utility methods to get a {@link JsonNodeFactory} and
 * a preconfigured {@link ObjectReader}. It can also be used to return
 * preconfigured instances of {@link ObjectMapper} (see {@link #newMapper()}.
 * </p>
 */
public final class JacksonUtils
{
    private static final JsonNodeFactory FACTORY = JsonNodeFactory.instance;

    private static final ObjectReader READER;
    private static final ObjectWriter WRITER;

    static {
        final ObjectMapper mapper = newMapper();
        READER = mapper.reader();
        WRITER = mapper.writer();
    }

    private JacksonUtils()
    {
    }

    /**
     * Return a preconfigured {@link ObjectReader} to read JSON inputs
     *
     * @return the reader
     * @see #newMapper()
     */
    public static ObjectReader getReader()
    {
        return READER;
    }

    /**
     * Return a preconfigured {@link JsonNodeFactory} to generate JSON data as
     * {@link JsonNode}s
     *
     * @return the factory
     */
    public static JsonNodeFactory nodeFactory()
    {
        return FACTORY;
    }

    /**
     * Return a map out of an object's members
     *
     * <p>If the node given as an argument is not a map, an empty map is
     * returned.</p>
     *
     * @param node the node
     * @return a map
     */
    public static Map<String, JsonNode> asMap(final JsonNode node)
    {
        if (!node.isObject())
            return Collections.emptyMap();

        final Iterator<Map.Entry<String, JsonNode>> iterator = node.fields();
        final Map<String, JsonNode> ret = Maps.newHashMap();

        Map.Entry<String, JsonNode> entry;

        while (iterator.hasNext()) {
            entry = iterator.next();
            ret.put(entry.getKey(), entry.getValue());
        }

        return ret;
    }

    /**
     * Pretty print a JSON value
     *
     * @param node the JSON value to print
     * @return the pretty printed value as a string
     * @see #newMapper()
     */
    public static String prettyPrint(final JsonNode node)
    {
        final StringWriter writer = new StringWriter();

        try {
            WRITER.writeValue(writer, node);
            writer.flush();
        } catch (JsonGenerationException e) {
            throw new RuntimeException("How did I get there??", e);
        } catch (JsonMappingException e) {
            throw new RuntimeException("How did I get there??", e);
        } catch (IOException ignored) {
            // cannot happen
        }

        return writer.toString();
    }

    /**
     * Return a preconfigured {@link ObjectMapper}
     *
     * <p>The returned mapper will have the following features enabled:</p>
     *
     * <ul>
     *     <li>{@link DeserializationFeature#USE_BIG_DECIMAL_FOR_FLOATS};</li>
     *     <li>{@link SerializationFeature#WRITE_BIGDECIMAL_AS_PLAIN};</li>
     *     <li>{@link SerializationFeature#INDENT_OUTPUT}.</li>
     * </ul>
     *
     * <p>This returns a new instance each time.</p>
     *
     * @return an {@link ObjectMapper}
     */
    public static ObjectMapper newMapper()
    {
        return new ObjectMapper().setNodeFactory(FACTORY)
            .enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
            .enable(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN)
            .enable(SerializationFeature.INDENT_OUTPUT);
    }
}
