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

package com.github.fge.jsonschema.library;

import com.github.fge.Frozen;
import com.github.fge.jsonschema.keyword.digest.Digester;
import com.github.fge.jsonschema.keyword.validator.KeywordValidator;
import com.github.fge.jsonschema.syntax.checkers.SyntaxChecker;

import java.lang.reflect.Constructor;


/**
 * Frozen keyword
 *
 * @see KeywordBuilder
 */
public final class Keyword
    implements Frozen<KeywordBuilder>
{
    /**
     * Name of this keyword
     */
    final String name;

    /**
     * Syntax checker
     */
    final SyntaxChecker syntaxChecker;

    /**
     * Digester
     */
    final Digester digester;

    /**
     * {@link KeywordValidator} constructor
     */
    final Constructor<? extends KeywordValidator> constructor;

    /**
     * Instantiate a new keyword builder
     *
     * @param name the name for this keyword
     * @return a new {@link KeywordBuilder}
     * @throws NullPointerException provided name is null
     * @see KeywordBuilder#KeywordBuilder(String)
     */
    public static KeywordBuilder newBuilder(final String name)
    {
        return new KeywordBuilder(name);
    }

    /**
     * Build a frozen keyword out of a thawed one
     *
     * @param builder the keyword builder to build from
     * @see KeywordBuilder#freeze()
     */
    Keyword(final KeywordBuilder builder)
    {
        name = builder.name;
        syntaxChecker = builder.syntaxChecker;
        digester = builder.digester;
        constructor = builder.constructor;
    }

    /**
     * Create a thawed version of this keyword
     *
     * @return a {@link KeywordBuilder}
     * @see KeywordBuilder#KeywordBuilder(Keyword)
     */
    @Override
    public KeywordBuilder thaw()
    {
        return new KeywordBuilder(this);
    }
}
