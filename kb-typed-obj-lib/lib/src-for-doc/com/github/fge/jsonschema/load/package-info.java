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

/**
 * Schema loading and JSON Reference resolving
 *
 * <p>This package contains all components necessary to load and preload JSON
 * schemas, along with the processor in charge of JSON Reference resolving
 * ({@link com.github.fge.jsonschema.load.RefResolver}).</p>
 *
 * <p>The main loading class is {@link
 * com.github.fge.jsonschema.load.SchemaLoader}. It relies on downloaders
 * configured in a {@link com.github.fge.jsonschema.load.URIManager} to load
 * schemas it does not already know of.</p>
 *
 * <p>Note that you can configure the latter to support an arbitrary set of URI
 * schemes, or remove support for schemes you don't want to support (for
 * security reasons or otherwise).</p>
 */
package com.github.fge.jsonschema.load;
