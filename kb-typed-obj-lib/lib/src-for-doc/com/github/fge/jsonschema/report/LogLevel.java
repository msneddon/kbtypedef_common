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

import com.github.fge.jsonschema.exceptions.ProcessingException;

/**
 * Message log levels
 *
 * <p>A special log level, {@link #NONE}, can be used by processors wishing to
 * implement "unchecked" validation (ie, capture {@link ProcessingException}s
 * and report them instead of throwing them).</p>
 *
 * <p>All messages within {@link ProcessingException}s have level {@link
 * #FATAL}.</p>
 */
public enum LogLevel
{
    DEBUG("debug"),
    INFO("info"),
    WARNING("warning"),
    ERROR("error"),
    FATAL("fatal"),
    NONE("none"),
    ;

    private final String s;

    LogLevel(final String s)
    {
        this.s = s;
    }

    @Override
    public String toString()
    {
        return s;
    }
}

