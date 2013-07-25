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

package com.github.fge.jsonschema.util.equivalence;

import com.google.common.base.Equivalence;

/**
 * Small wrapper class over Guava's builtin {@link Equivalence}s
 *
 * <p>Guava's {@link Equivalence#equals()} and {@link Equivalence#identity()}
 * are not parameterized: this just makes them so.</p>
 */
public final class Equivalences
{
    private Equivalences()
    {
    }

    /**
     * Return a parameterized {@link Equivalence#equals()}
     *
     * @param <T> the parameter type
     * @return the parameterized equivalence
     */
    @SuppressWarnings("unchecked")
    public static <T> Equivalence<T> equals()
    {
        return (Equivalence<T>) Equivalence.equals();
    }

    /**
     * Return a parameterized {@link Equivalence#identity()}
     *
     * @param <T> the parameter type
     * @return the parameterized equivalence
     */
    @SuppressWarnings("unchecked")
    public static <T> Equivalence<T> identity()
    {
        return (Equivalence<T>) Equivalence.identity();
    }
}
